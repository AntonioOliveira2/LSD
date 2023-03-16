package pt.tecnico.addressbook.server.domain.exception;

public class NonExistentPerson extends IllegalArgumentException {
    private final String email;

    public NonExistentPerson(String email) {
        super("Person with email " + email + " already registered in the address book");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
