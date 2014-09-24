package com.tsystems.javaschool.vm.exception;

public class EntityNotFoundException extends SBBException {
    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityName, Object key) {
        this(entityName + " not doesn't exist. Search key: " + key);
    }

}
