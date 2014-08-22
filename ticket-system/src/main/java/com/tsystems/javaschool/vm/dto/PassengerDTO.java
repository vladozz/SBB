package com.tsystems.javaschool.vm.dto;

import com.tsystems.javaschool.vm.domain.SBBEntity;
import com.tsystems.javaschool.vm.domain.Ticket;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class PassengerDTO implements Serializable {
    private String firstName;
    private String lastName;
    private Calendar birthDate;

    public PassengerDTO(String firstName, String lastName, Calendar birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
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
        return "Passenger{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
