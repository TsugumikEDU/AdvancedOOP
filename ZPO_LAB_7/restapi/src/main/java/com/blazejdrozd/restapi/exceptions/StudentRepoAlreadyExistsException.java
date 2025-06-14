package com.blazejdrozd.restapi.exceptions;

public class StudentRepoAlreadyExistsException extends StudentRepoException {
    public StudentRepoAlreadyExistsException(Integer albumNumber) {
        super("Student with album number " + albumNumber + " already exists");
    }
}
