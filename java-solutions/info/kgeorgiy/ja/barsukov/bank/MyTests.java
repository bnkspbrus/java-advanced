package info.kgeorgiy.ja.barsukov.bank;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.Assert.*;

public class MyTests {
    private final static int DEFAULT_PORT = 8888;
    private static Bank bank;

    public static void main(String[] args) {
        JUnitCore jUnitCore = new JUnitCore();
        jUnitCore.addListener(new TextListener(System.out));
        jUnitCore.run(MyTests.class);
        // :NOTE: exit(0)
        System.exit(0);
    }

    @BeforeClass
    public static void before() throws RuntimeException {
        bank = new RemoteBank(DEFAULT_PORT);
        try {
            Registry registry = LocateRegistry.createRegistry(DEFAULT_PORT);
            UnicastRemoteObject.exportObject(bank, DEFAULT_PORT);
            registry.rebind("//localhost/bank", bank);
            System.out.println("Server started");
        } catch (final RemoteException e) {
            System.out.println("Cannot export object: " + e.getMessage());
            e.printStackTrace();
            // :NOTE: exit(1)
            System.exit(1);
        }
    }
    @Test
    public void getAndCreateNonExistingPerson() throws RemoteException {
        Person p1 = bank.getPerson("1", true);
        assertNull(p1);
        bank.createPerson("a", "b", "1", true);
        p1 = bank.getPerson("1", true);
        assertNotNull(p1);
        Person p2 = bank.getPerson("2", false);
        assertNull(p2);
        p2 = bank.getPerson("1", false);
        assertNotNull(p2);
        equalsPerson(p1,p2);
    }

    @Test
    public void getCreateAndChangeAccount() throws RemoteException {
        Person p1 = bank.getPerson("1", true);
        Person p2 = bank.getPerson("1", false);
        Person p3 = bank.getPerson("1", true);
        Person p4 = bank.getPerson("1", false);
        Account a1 = getAccount(bank, p1, "12", true);
        Account a2 = getAccount(bank, p2, "12", false);
        assertNull(a1);
        assertNull(a2);
        createAccount(bank, p1, "12", true);
        a1 = getAccount(bank, p1, "12", true);
        assertNotNull(a1);
        a2 = getAccount(bank, p2, "12", false);
        assertNull(a2);
        Account a3 = getAccount(bank, p3, "12", true);
        assertNotNull(a3);
        createAccount(bank, p2, "12", false);
        a2 = getAccount(bank, p2, "12", false);
        assertNotNull(a2);
        Account a4 = getAccount(bank, p4, "12", false);
        assertNull(a4);
        createAccount(bank, p4, "12", false);
        p1 = bank.getPerson("1", true);
        a1 = getAccount(bank, p1, "12", true);
        assertEquals(a1.getAmount(), 0);
        a1.setAmount(a1.getAmount() + 100);
        assertEquals(a1.getAmount(), 100);
        a3 = getAccount(bank, p3, "12", true);
        assertEquals(a3.getAmount(), a1.getAmount());
        a2 = getAccount(bank, p2, "12", false);
        assertNotEquals(a1.getAmount(), a2.getAmount());
        a2.setAmount(a2.getAmount() + 100);
        assertEquals(a2.getAmount(), 100);
        a4 = getAccount(bank, p4, "12", false);
        assertEquals(a4.getAmount(), 0);
    }

    // :NOTE: тесты на многопоточность

    private static void equalsPerson(Person p1, Person p2) throws RemoteException {
        assertEquals(p1.getPassportId(), p2.getPassportId());
        assertEquals(p1.getName(), p2.getName());
        assertEquals(p1.getSurname(), p2.getSurname());
    }

    private static Account getAccount(Bank bank, Person person, String subId, boolean remote) throws RemoteException {
        if (remote) {
            return bank.getAccount(person.getPassportId(), subId);
        } else {
            return ((LocalPerson) person).getAccount(subId);
        }
    }

    private static void createAccount(Bank bank, Person person, String subId,
            boolean remote) throws RemoteException {
        if (remote) {
            bank.createAccount(person.getPassportId(), subId);
        } else {
            ((LocalPerson) person).createAccount(subId);
        }
    }
}
