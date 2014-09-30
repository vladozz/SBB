package com.tsystems.javaschool.vm.validator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LongValidator {
    public List<String> validateLong(Number number, String fieldName) {
        List<String> errorList = new ArrayList<String>();
        if (number == null) {
            errorList.add(fieldName + " field is null");
        }
        return errorList;
    }


}
