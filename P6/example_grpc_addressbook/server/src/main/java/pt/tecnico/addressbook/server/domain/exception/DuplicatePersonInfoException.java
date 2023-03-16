package pt.tecnico.addressbook.server.domain.exception;

public class DuplicatePersonInfoException extends IllegalArgumentException {
    private final String email;

    public DuplicatePersonInfoException(String email) {
        super("Person with email " + email + " does not exist");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
