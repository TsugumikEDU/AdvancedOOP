package com.blazejdrozd.restapi.models;

import com.blazejdrozd.restapi.utils.DiacriticRemover;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private Integer albumNumber;

    public String getEmail() {
        String a = firstName.substring(0, Math.min(firstName.length(), 3)).toLowerCase();
        String b = lastName.substring(0, Math.min(lastName.length(), 3)).toLowerCase();

        String mail = a + b + id.toString() + "@pbs.edu.pl";

        return DiacriticRemover.removePolishDiacritics(mail);
    }
}
