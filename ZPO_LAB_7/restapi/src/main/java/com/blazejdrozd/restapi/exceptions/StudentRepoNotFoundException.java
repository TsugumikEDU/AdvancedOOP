package com.blazejdrozd.restapi.exceptions;

public class StudentRepoNotFoundException extends StudentRepoException {
    public StudentRepoNotFoundException(Integer albumNumber) {
        super("Student with album number " + albumNumber + " not found");
    }
}
