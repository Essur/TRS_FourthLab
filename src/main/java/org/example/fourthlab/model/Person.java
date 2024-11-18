package org.example.fourthlab.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dateOfBirth;

    // Constructor
    public Person(String firstName, String middleName, String lastName, String dateOfBirth) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }


}
