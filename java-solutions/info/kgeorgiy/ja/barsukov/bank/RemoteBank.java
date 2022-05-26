package info.kgeorgiy.ja.barsukov.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class RemoteBank implements Bank {
    private final int port;
    private final ConcurrentMap<String, ConcurrentMap<String, Account>> accounts = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Person> persons = new ConcurrentHashMap<>();


    public RemoteBank(final int port) {
        this.port = port;
    }

    @Override
    public Account createAccount(final String passportId, String subId) throws RemoteException {
        String accountId = String.format("%s:%s", passportId, subId);
        System.out.println("Creating account " + accountId);
        final Account account = new AccountImpl(accountId);
        accounts.putIfAbsent(passportId, new ConcurrentHashMap<>());
        if (accounts.get(passportId).putIfAbsent(subId, account) == null) {
            UnicastRemoteObject.exportObject(account, port);
            return account;
        } else {
            return getAccount(passportId, subId);
        }
    }

    @Override
    public Account getAccount(final String passportId, String subId) {
        String accountId = String.format("%s:%s", passportId, subId);
        System.out.println("Retrieving account " + accountId);
        accounts.putIfAbsent(passportId, new ConcurrentHashMap<>());
        return accounts.get(passportId).get(subId);
    }

    @Override
    public Person getPerson(String passportId, boolean remote) throws RemoteException {
        if (remote) {
            return persons.get(passportId);
        } else {
            Person remotePerson = persons.get(passportId);
            if (remotePerson == null) {
                return null;
            }
            Map<String, Account> remoteAccounts = accounts.get(passportId);
            Map<String, Account> localAccounts = remoteAccounts.entrySet().stream().collect(
                    Collectors.toMap(Map.Entry::getKey, entry -> {
                        try {
                            return new AccountImpl(entry.getValue());
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }));
            return new LocalPerson(remotePerson.getName(), remotePerson.getSurname(), passportId, localAccounts);
        }
    }

    @Override
    public Person createPerson(String name, String surname, String passportId, boolean remote) throws RemoteException {
        final RemotePerson person = new RemotePerson(name, surname, passportId);
        if (persons.putIfAbsent(passportId, person) == null) {
            accounts.putIfAbsent(passportId, new ConcurrentHashMap<>());
            UnicastRemoteObject.exportObject(person, port);
            if (remote) {
                return person;
            } else {
                return new LocalPerson(name, surname, passportId, new HashMap<>());
            }
        } else {
            return getPerson(passportId, remote);
        }
    }
}
