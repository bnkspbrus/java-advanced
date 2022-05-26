package info.kgeorgiy.ja.barsukov.bank;

import java.io.Serial;
import java.io.Serializable;

public abstract class AbstractPerson implements Person, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    final String name, surname;
    final String passportId;

    public AbstractPerson(String name, String surname, String passportId) {
        this.name = name;
        this.surname = surname;
        this.passportId = passportId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getPassportId() {
        return passportId;
    }
}
