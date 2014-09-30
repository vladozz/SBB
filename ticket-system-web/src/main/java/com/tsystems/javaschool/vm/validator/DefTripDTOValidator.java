package com.tsystems.javaschool.vm.validator;

import com.tsystems.javaschool.vm.dto.DefTripDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefTripDTOValidator {
    @Autowired
    private LongValidator longValidator;
    @Autowired
    private DateTimeStringValidator dateTimeStringValidator;

    public List<String> validate(DefTripDTO defTripDTO) {
        List<String> errors = longValidator.validateLong(defTripDTO.getArriveStationId(), "ArriveStationId");
        errors.addAll(longValidator.validateLong(defTripDTO.getDepartureStationId(), "DepartureStationId"));
        errors.addAll(dateTimeStringValidator.validateDateString(defTripDTO.getDepartureDate()));
        errors.addAll(dateTimeStringValidator.validateDateString(defTripDTO.getArriveDate()));
        errors.addAll(dateTimeStringValidator.validateTimeString(defTripDTO.getDepartureTime()));
        errors.addAll(dateTimeStringValidator.validateTimeString(defTripDTO.getArriveTime()));
        return errors;
    }
}
