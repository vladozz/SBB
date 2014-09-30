package com.tsystems.javaschool.vm.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Calendar;

@Entity
@Table(name="passenger")
public class Passenger extends SBBEntity {
    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="birthdate")
    private Calendar birthDate;


    public Passenger() {
    }

    public Passenger(String firstName, String lastName, Calendar birthDate) {
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
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    @Override
    public int compareTo(SBBEntity sbbEntity) {
        Passenger that = ((Passenger) sbbEntity);

        int result = this.lastName.compareToIgnoreCase(that.lastName);
        if (result == 0) {
            result = this.firstName.compareToIgnoreCase(that.getFirstName());
            if (result == 0) {
                result = -this.birthDate.compareTo(that.birthDate);
            }
        }
        return result;
    }
}
