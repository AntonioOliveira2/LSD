package pt.tecnico.addressbook.server.domain;

import pt.tecnico.addressbook.grpc.AddressBookList;
import pt.tecnico.addressbook.grpc.PersonInfo;
import pt.tecnico.addressbook.grpc.PersonInfo.PhoneType;
import pt.tecnico.addressbook.server.domain.exception.DuplicatePersonInfoException;
import pt.tecnico.addressbook.server.domain.exception.NonExistentPerson;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AddressBook {

    private ConcurrentHashMap<String, Person> people = new ConcurrentHashMap<>();

    public AddressBook() {
    }

    public void addPerson(String name, String email, int phoneNumber, PhoneType type) throws DuplicatePersonInfoException {
        if(people.putIfAbsent(email, new Person(name, email, phoneNumber, type)) != null) {
            throw new DuplicatePersonInfoException(email);
        }
    }

    public AddressBookList proto() {
        return AddressBookList.newBuilder()
                .addAllPeople(people.values().stream().map(Person::proto).collect(Collectors.toList()))
                .build();
    }

    public PersonInfo searchPerson(String email) {
        Person getPerson;
        if ((getPerson = people.get(email)) == null)
            throw new NonExistentPerson(email);

        return PersonInfo.newBuilder().setName(getPerson.getName()).setEmail(getPerson.getEmail()).
                setPhone(PersonInfo.PhoneNumber.newBuilder().setNumber(getPerson.getPhoneNumber()).
                        setType(getPerson.getType()).build()).build();
    }
}
