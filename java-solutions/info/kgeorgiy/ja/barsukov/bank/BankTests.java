package info.kgeorgiy.ja.barsukov.bank;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runners.MethodSorters;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankTests {

    private static Person localPerson, remotePerson;

    private static Account remoteAccount, localAccount;

    private static final String LOCAL_ID = "localPerson", REMOTE_ID = "remotePerson";

    private static final String LOCAL_SUB_ID = "localAccount", REMOTE_SUB_ID = "remoteAccount";

    private static final String REGISTRY_BANK_NAME = "//localhost/bank";

    private static Bank bank;

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        Class<?> test = BankTests.class;
        System.err.printf("Running %s%n", test);
        long start = System.currentTimeMillis();
        final Result result = new JUnitCore().run(test);
        if (result.wasSuccessful()) {
            System.out.println("============================");
            final long time = System.currentTimeMillis() - start;
            System.out.printf("OK %s in %dms %n", test, time);
            return;
        }

        for (final Failure failure : result.getFailures()) {
            System.err.println("Test " + failure.getDescription().getMethodName() + " failed: " + failure.getMessage());
            if (failure.getException() != null) {
                failure.getException().printStackTrace();
            }
        }
    }

    @BeforeClass
    public static void before() throws RemoteException {
        bank = new RemoteBank(Server.DEFAULT_PORT);
        Registry registry = LocateRegistry.createRegistry(Server.DEFAULT_PORT);
        UnicastRemoteObject.exportObject(bank, Server.DEFAULT_PORT);
        registry.rebind(REGISTRY_BANK_NAME, bank);
    }

    @Test
    public void test01_initRemoteAndLocalPerson() throws RemoteException, NotBoundException {

        Bank bank = (Bank) LocateRegistry.getRegistry(Server.DEFAULT_PORT).lookup(REGISTRY_BANK_NAME);
        remotePerson = bank.getPerson(REMOTE_ID, true);
        localPerson = bank.getPerson(LOCAL_ID, false);
        assertNull(remotePerson);
        assertNull(localPerson);
        remotePerson = bank.createPerson("remote", "person", REMOTE_ID, true);
        assertNotNull(remotePerson);
        assertNotNull(bank.getPerson(REMOTE_ID, true));
        assertNotNull(bank.getPerson(REMOTE_ID, false));
        assertNull(localPerson);
        assertNull(bank.getPerson(LOCAL_ID, true));
        assertNull(bank.getPerson(LOCAL_ID, false));
        localPerson = bank.createPerson("local", "person", LOCAL_ID, false);
        assertNotNull(bank.getPerson(LOCAL_ID, true));
        assertNotNull(bank.getPerson(LOCAL_ID, false));
        localAccount = Client.getAccount(bank, localPerson, LOCAL_SUB_ID, false);
        remoteAccount = Client.getAccount(bank, remotePerson, REMOTE_SUB_ID, true);
        assertNull(localAccount);
        assertNull(remoteAccount);
        localAccount = Client.createAccount(bank, localPerson, LOCAL_SUB_ID, false);
        remoteAccount = Client.createAccount(bank, remotePerson, REMOTE_SUB_ID, true);
        assertNotNull(localAccount);
        assertNotNull(remoteAccount);
        assertNotNull(Client.getAccount(bank, localPerson, LOCAL_SUB_ID, false));
        assertNotNull(Client.getAccount(bank, remotePerson, REMOTE_SUB_ID, true));
        localPerson = bank.getPerson(REMOTE_ID, false);
        localAccount = Client.getAccount(bank, localPerson, REMOTE_SUB_ID, false);
    }

    @Test
    public void test02_amountTest() throws RemoteException, NotBoundException {
        Bank bank = (Bank) LocateRegistry.getRegistry(Server.DEFAULT_PORT).lookup(REGISTRY_BANK_NAME);
        assertEquals(0, localAccount.getAmount());
        assertEquals(0, remoteAccount.getAmount());
        Person localPerson2 = bank.getPerson(REMOTE_ID, false);
        Account localAccount2 = Client.getAccount(bank, localPerson2, REMOTE_SUB_ID, false);
        assertEquals(0, localAccount2.getAmount());
        localAccount.setAmount(100);
        assertEquals(0, localAccount2.getAmount());
        assertEquals(0, remoteAccount.getAmount());
        remoteAccount.setAmount(200);
        assertEquals(100, localAccount.getAmount());
        Person remotePerson2 = bank.getPerson(REMOTE_ID, true);
        Account remoteAccount2 = Client.getAccount(bank, remotePerson2, REMOTE_SUB_ID, true);
        assertEquals(200, remoteAccount2.getAmount());
    }

    @Test
    public void test03_addAccountTest() throws RemoteException, NotBoundException {
        Bank bank = (Bank) LocateRegistry.getRegistry(Server.DEFAULT_PORT).lookup(REGISTRY_BANK_NAME);
        assertNotNull(localAccount);
        assertNotNull(remoteAccount);
        final String newAccountId = "newAccountId";
        assertNull(Client.getAccount(bank, localPerson, newAccountId, false));
        assertNull(Client.getAccount(bank, remotePerson, newAccountId, true));
        Client.createAccount(bank, localPerson, newAccountId, false);
        assertNull(Client.getAccount(bank, remotePerson, newAccountId, true));

    }

    @Test
    public void test04_multiThread() throws RemoteException, InterruptedException, NotBoundException {
        Bank bank = (Bank) LocateRegistry.getRegistry(Server.DEFAULT_PORT).lookup(REGISTRY_BANK_NAME);
        ExecutorService writers = Executors.newFixedThreadPool(50);
        assertEquals(200, remoteAccount.getAmount());
        Account[] accounts = new Account[50];
        for (int i = 0; i < 50; i++) {
            accounts[i] = Client.getAccount(bank, remotePerson, REMOTE_SUB_ID, true);
            assertEquals(200, accounts[i].getAmount());
        }
        for (int i = 0; i < 50; i++) {
            writers.execute(new Writer(accounts[i]));
        }
        awaitTermination(writers);
        for (Account account : accounts) {
            assertEquals(250, account.getAmount());
        }
    }

    private static void awaitTermination(ExecutorService executor) throws InterruptedException {
        executor.shutdown();

        if (!executor.awaitTermination(100, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }

    private record Writer(Account account) implements Runnable {

        private static final AtomicInteger amount = new AtomicInteger(200);

        @Override
        public void run() {
            try {
                account.setAmount(amount.incrementAndGet());
            } catch (RemoteException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @AfterClass
    public static void after() throws RemoteException, NotBoundException {
//        Bank bank = (Bank) LocateRegistry.getRegistry(Server.DEFAULT_PORT).lookup(REGISTRY_BANK_NAME);
        LocateRegistry.getRegistry(Server.DEFAULT_PORT).unbind(REGISTRY_BANK_NAME);
        UnicastRemoteObject.unexportObject(bank, true);
    }
}
