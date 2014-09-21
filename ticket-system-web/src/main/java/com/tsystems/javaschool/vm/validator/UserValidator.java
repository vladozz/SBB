package com.tsystems.javaschool.vm.validator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator {
    public List<String> validateLogin(String login) {
        final String LOGIN_REGEXP = "[a-z][a-z0-9_\\.]{2,19}";
        List<String> errorList = new ArrayList<String>();
        if (login.isEmpty()) {
            errorList.add("Login cannot be empty");
            return errorList;
        } else if (login.length() < 3) {
            errorList.add("Login length cannot be less than 3 letters");
        } else if (login.length() > 20) {
            errorList.add("Login length cannot be more than 20 letters");
        }
        if (Character.isDigit(login.charAt(0))) {
            errorList.add("Login cannot start with digit");
        }
        if (errorList.isEmpty() && !login.toLowerCase().matches(LOGIN_REGEXP)) {
            errorList.add("Login must contain only letters, numbers and signs _ and .");
        }
        return errorList;
    }

    public List<String> validateEmail(String email) {
        final String EMAIL_REGEXP = "^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$";
        List<String> errorList = new ArrayList<String>();

        if (email.isEmpty()) {
            errorList.add("Email cannot be empty");
        } else if (!email.toLowerCase().matches(EMAIL_REGEXP)) {
            errorList.add("Invalid email");
        }
        return errorList;
    }

    public List<String> validatePassword(String password) {
        final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        List<String> errorList = new ArrayList<String>();

        if (password.isEmpty()) {
            errorList.add("Password cannot be empty");
            return errorList;
        } else if (password.length() < 8) {
            errorList.add("Password length cannot be less than 8 letters");
        } else if (password.length() > 20) {
            errorList.add("Password length cannot be more than 20 letters");
        } else if (!password.matches(PASSWORD_REGEXP)) {
            errorList.add("Password must contain at least one upper case letter, one lower case letter and one digit. Size must be between 8 and 20 characters");
        }

        return errorList;
    }
}
