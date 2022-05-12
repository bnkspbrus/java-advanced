package info.kgeorgiy.ja.barsukov.bank;

public class LocalAccount extends AbstractAccount {
    public LocalAccount(String id, int amount) {
        super(id);
        setAmount(amount);
    }
}
