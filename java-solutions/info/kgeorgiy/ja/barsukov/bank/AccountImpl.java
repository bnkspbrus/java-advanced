package info.kgeorgiy.ja.barsukov.bank;

import java.io.Serializable;
import java.rmi.RemoteException;

public class AccountImpl implements Account, Serializable {
    private final String id;
    private int amount;

    public AccountImpl(final String id) {
        this.id = id;
        amount = 0;
    }

    public AccountImpl(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public AccountImpl(Account account) throws RemoteException {
        this.id = account.getId();
        this.amount = account.getAmount();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public synchronized int getAmount() {
        System.out.println("Getting amount of money for account " + id);
        return amount;
    }

    @Override
    public synchronized void setAmount(final int amount) {
        System.out.println("Setting amount of money for account " + id);
        this.amount = amount;
    }
}
