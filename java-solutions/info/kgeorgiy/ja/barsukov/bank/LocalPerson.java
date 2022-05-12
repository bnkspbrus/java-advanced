package info.kgeorgiy.ja.barsukov.bank;

import java.util.Map;

public class LocalPerson extends AbstractPerson {

    private final Map<String, Account> accounts; //Local account

    public LocalPerson(String name, String surname, String passportId, Map<String, Account> accounts) {
        super(name, surname, passportId);
        this.accounts = accounts;
    }

    public Account createAccount(String subId) {
        String accountId = String.format("%s:%s", passportId, subId);
        final Account account = new LocalAccount(accountId, 0);
        if (accounts.putIfAbsent(subId, account) == null) {
            return account;
        } else {
            return getAccount(subId);
        }
    }

    public Account getAccount(String subId) {
        return accounts.get(subId);
    }
}
