package com.guerra.medview.exception;

public class DoctorAlreadyExistsException extends RuntimeException {

    public DoctorAlreadyExistsException(String field) {
        super("A doctor with this " + field + " is already registered");
    }
}
