package com.tsystems.javaschool.vm.validator;

import com.tsystems.javaschool.vm.domain.Station;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StationValidator extends SBBValidator<Station> {

    @Override
    public List<String> validate(Station station) {
        List<String> errorList = new ArrayList<String>();
        if (station.getTitle() == null) {
            errorList.add("Error: title field is null");
        }
        if (station.getTimeZone() == null) {
            errorList.add("Error: timeZone field is null");
        }
        if (errorList.isEmpty()) {
            validator.validate(station, errorList);
        }
        return errorList;
    }


}
