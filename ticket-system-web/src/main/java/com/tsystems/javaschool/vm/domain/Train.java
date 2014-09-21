package com.tsystems.javaschool.vm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "train")
public class Train extends SBBEntity {
    @Column(name = "num")
    @NotNull(message = "Number cannot be empty")
    @Size(min = 1, max = 30,
            message = "The length of train number must be between 1 and 30 characters")
    private String number;

    @Column(name = "places_qty")
    @NotNull(message = "Quantity of places cannot be empty")
    @Max(value = 5000, message = "Quantity of places cannot be more then 5000")
    @Min(value = 1, message = "Quantity of places cannot be less than 1")
    private Short placesQty;

    @OneToMany(mappedBy = "train")
    List<Trip> trips;

    public Train() {

    }

    public Train(String number, short placesQty) {
        this.number = number;
        this.placesQty = placesQty;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Short getPlacesQty() {
        return placesQty;
    }

    public void setPlacesQty(Short placesQty) {
        this.placesQty = placesQty;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Train)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Train train = (Train) o;

        return number.equals(train.number) && placesQty.equals(train.placesQty);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + placesQty.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Train{" +
                "number='" + number + '\'' +
                ", placesQty=" + placesQty +
                '}';
    }
}
