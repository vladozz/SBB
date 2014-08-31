package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.util.Calendar;

public class PassengerDTO implements Serializable {

    private static final long serialVersionUID = -4620477088385691404L;
    private Long id;
    private String firstName;
    private String lastName;
    private Calendar birthDate;

    public PassengerDTO() {
    }

    public PassengerDTO(Long id, String firstName, String lastName, Calendar birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public PassengerDTO(String firstName, String lastName, Calendar birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "PassengerDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
