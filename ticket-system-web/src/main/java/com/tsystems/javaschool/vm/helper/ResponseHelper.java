package com.tsystems.javaschool.vm.helper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseHelper {
    public String createErrorResponse(List<String> errorList) {

        StringBuilder errorResponse = new StringBuilder();
        for (String error : errorList) {
            errorResponse.append("<p>").append(error).append(".</p>");
        }
        return errorResponse.toString();
    }

    public String createErrorResponse(Throwable e) {

        return "error " + e;
    }
}
