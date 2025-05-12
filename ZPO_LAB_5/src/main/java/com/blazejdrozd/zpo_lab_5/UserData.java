package com.blazejdrozd.zpo_lab_5;

public class UserData {
    @MyPattern(regex = "^[A-Za-z]{3,10}$", message = "Imię musi mieć 3-10 liter.")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}