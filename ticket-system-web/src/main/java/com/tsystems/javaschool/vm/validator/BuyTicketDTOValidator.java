package com.tsystems.javaschool.vm.validator;

import com.tsystems.javaschool.vm.dto.BuyTicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BuyTicketDTOValidator {
    @Autowired
    private LongValidator longValidator;

    private static final Long YEAR_150 = 1000L * 86400 * 365 * 150;

    public List<String> validate(BuyTicketDTO buyTicketDTO) {
        List<String> errors = new ArrayList<String>();
        if (buyTicketDTO.getFirstName() == null) {
            errors.add("First name field is null.");
        } else if (buyTicketDTO.getFirstName().isEmpty()) {
            errors.add("First name field cannot be empty.");
        }

        if (buyTicketDTO.getLastName() == null) {
            errors.add("Last name field is null.");
        }

        List<String> bdErrors = longValidator.validateLong(buyTicketDTO.getBirthDate(), "Birthdate field");
        errors.addAll(bdErrors);
        if (bdErrors.isEmpty()) {
            Date today = new Date();
            if (today.compareTo(new Date(buyTicketDTO.getBirthDate())) < 0) {
                errors.add("Birth date cannot be in future");
            } else if (today.getTime() - buyTicketDTO.getBirthDate() > YEAR_150) {
                errors.add("Birth date is too old: " + new Date(buyTicketDTO.getBirthDate()));
            }
        }
        errors.addAll(longValidator.validateLong(buyTicketDTO.getDepartureBoardId(),  "DepartureBoardId"));
        errors.addAll(longValidator.validateLong(buyTicketDTO.getArriveBoardId(),  "ArriveBoardId"));
        return errors;
    }
}
