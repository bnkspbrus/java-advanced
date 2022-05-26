package info.kgeorgiy.ja.barsukov.bank;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.Objects;

public final class Client {

    private static final String USAGE = "Usage:\njava Client <name> <surname> <passportId> <subId> <change>";

    private final static int DEFAULT_PORT = 8888;

    private static final boolean REMOTE = false;

    /**
     * Utility class.
     */
    private Client() {
    }

    public static void main(final String... args) throws RemoteException {
        final Bank bank;
        try {
            bank = (Bank) LocateRegistry.getRegistry(args != null && args.length > 5? Integer.parseInt(args[5]): DEFAULT_PORT).lookup("//localhost/bank");
        } catch (final NotBoundException e) {
            System.out.println("Bank is not bound");
            return;
        }
        if (args == null || args.length != 5 || anyMatchNull(args)) {
            System.err.println(USAGE);
            System.exit(1);
        }
        String name = args[0], surname = args[1], passportId = args[2], subId = args[3];
        int change = Integer.parseInt(args[4]);
        Person person = bank.getPerson(passportId, REMOTE);
        if (person == null) {
            System.out.println("Creating person");
            person = bank.createPerson(name, surname, passportId, REMOTE);
        } else {
            System.out.println("Person already exists");
        }
        Account account = getAccount(bank, person, subId, REMOTE);
        if (account == null) {
            System.out.println("Creating account");
            account = createAccount(bank, person, subId, REMOTE);
        } else {
            System.out.println("Account already exists");
        }
        System.out.println("Account id: " + account.getId());
        System.out.println("Money: " + account.getAmount());
        System.out.println("Adding money");
        account.setAmount(account.getAmount() + change);
        System.out.println("Money: " + account.getAmount());
    }

    /**
     *
     * @param bank
     * @param person
     * @param subId
     * @param remote
     * @return
     * @throws RemoteException
     */
    public static Account getAccount(Bank bank, Person person, String subId, boolean remote) throws RemoteException {
        if (remote) {
            return bank.getAccount(person.getPassportId(), subId);
        } else {
            return ((LocalPerson) person).getAccount(subId);
        }
    }

    /**
     *
     * @param bank
     * @param person
     * @param subId
     * @param remote
     * @return
     * @throws RemoteException
     */
    public static Account createAccount(Bank bank, Person person, String subId,
            boolean remote) throws RemoteException {
        if (remote) {
            return bank.createAccount(person.getPassportId(), subId);
        } else {
            return ((LocalPerson) person).createAccount(subId);
        }
    }

    private static boolean anyMatchNull(String[] args) {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }
}
