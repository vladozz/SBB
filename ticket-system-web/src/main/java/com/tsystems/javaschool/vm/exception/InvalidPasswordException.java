package com.tsystems.javaschool.vm.exception;

public class InvalidPasswordException extends SBBException {
    public InvalidPasswordException() {
        this("Current password is incorrect.");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
