package com.tsystems.javaschool.vm.converter;

import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.Ticket;
import com.tsystems.javaschool.vm.domain.Trip;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.springframework.stereotype.Component;

@Component
public class TicketConverter {

    private static final String datetimeFormat = "E yyyy-MM-dd HH:mm";

    public String convertToString(Ticket ticket) {

        StringBuilder body = new StringBuilder();
        body.append("You've successfully bought the ticket.");
        body.append("\nTicket number: ").append(ticket.getId());

        Trip trip = ticket.getArrive().getTrip();
        body.append("\nTrip number: ").append(trip.getId());
        body.append("\nPath title: ").append(trip.getPath().getTitle(trip.getForward()));
        body.append("\nTrip number: ").append(trip.getTrain().getNumber());

        Board departure = ticket.getDeparture();
        body.append("\nDeparture station: ").append(departure.getStation().getTitle());
        DateTime departureDateTime = new DateTime(departure.getDepartureTime(),
                DateTimeZone.forTimeZone(departure.getStation().getTimeZone()));
        body.append("\nDeparture time: ").append(departureDateTime.toString(datetimeFormat));
        Board arrive = ticket.getArrive();
        body.append("\nArrive station: ").append(arrive.getStation().getTitle());
        DateTime arriveDateTime = new DateTime(arrive.getArriveTime(),
                DateTimeZone.forTimeZone(arrive.getStation().getTimeZone()));
        body.append("\nArrive time: ").append(arriveDateTime.toString(datetimeFormat));
        int travelTimeMinutes = Minutes.minutesBetween(departureDateTime, arriveDateTime).getMinutes();
        String travelTime = travelTimeMinutes / 60 + " hours " + travelTimeMinutes % 60 + "minutes";
        body.append("\nTravel time: ").append(travelTime);
        body.append("\nBuy ticket date: ").append(new DateTime().toString(datetimeFormat));

        return body.toString();
    }


}
