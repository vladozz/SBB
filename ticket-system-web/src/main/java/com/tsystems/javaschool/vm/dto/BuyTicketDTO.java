package com.tsystems.javaschool.vm.dto;

public class BuyTicketDTO {

    private String firstName;
    private String lastName;
    private Long birthDate;
    Long departureBoardId;
    Long arriveBoardId;

    public BuyTicketDTO() {
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

    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    public Long getDepartureBoardId() {
        return departureBoardId;
    }

    public void setDepartureBoardId(Long departureBoardId) {
        this.departureBoardId = departureBoardId;
    }

    public Long getArriveBoardId() {
        return arriveBoardId;
    }

    public void setArriveBoardId(Long arriveBoardId) {
        this.arriveBoardId = arriveBoardId;
    }

    @Override
    public String toString() {
        return "BuyTicketDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", departureBoardId=" + departureBoardId +
                ", arriveBoardId=" + arriveBoardId +
                '}';
    }
}
