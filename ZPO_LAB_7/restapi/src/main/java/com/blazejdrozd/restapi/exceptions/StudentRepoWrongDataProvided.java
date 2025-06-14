package com.blazejdrozd.restapi.exceptions;

public class StudentRepoWrongDataProvided extends RuntimeException {
    public StudentRepoWrongDataProvided(String message) {
        super(message);
    }
}
