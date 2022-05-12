package info.kgeorgiy.ja.barsukov.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote {
    /**
     * Creates a new account with specified identifier if it is not already exists.
     *
     * @param passportId passport identifier.
     * @param subId      account identifier.
     * @return created or existing account.
     */
    Account createAccount(String passportId, String subId) throws RemoteException;

    /**
     * Returns account by identifier.
     *
     * @param passportId passport id.
     * @param sudId      account id
     * @return account with specified identifier or {@code null} if such account does not exists.
     */
    Account getAccount(String passportId, String sudId) throws RemoteException;

    Person getPerson(String passportId, boolean remote) throws RemoteException;

    Person createPerson(String name, String surname, String passportId, boolean remote) throws RemoteException;
}
