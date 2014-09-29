package com.tsystems.javaschool.vm.validator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DateTimeStringValidator {
    public static final String DATE_REGEX = "^(1[89]|20)[0-9]{2}-(0[0-9]|1[0-2])-([0-2][0-9]|3[01])$";
    public static final String TIME_REGEX =  "^(2[0-3]|[01][0-9]):([0-5][0-9])$";

    public List<String> validateDateString(String dateString) {
        List<String> errorList = new ArrayList<String>();
        if (dateString == null) {
            errorList.add("Date field is null");
        } else if (dateString.isEmpty()) {
            errorList.add("Date field cannot be empty");
        } else if (!dateString.matches(DATE_REGEX)) {
            errorList.add("Date field has invalid format");
        }
        return errorList;
    }

    public List<String> validateTimeString(String timeString) {
        List<String> errorList = new ArrayList<String>();
        if (timeString == null) {
            errorList.add("Time field is null");
        } else if (timeString.isEmpty()) {
            errorList.add("Time field cannot be empty");
        } else if (!timeString.matches(TIME_REGEX)) {
            errorList.add("Time field has invalid format");
        }
        return errorList;
    }

}
