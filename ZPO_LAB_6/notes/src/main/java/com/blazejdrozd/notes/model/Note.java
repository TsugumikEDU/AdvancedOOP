package com.blazejdrozd.notes.model;

import com.blazejdrozd.notes.Importance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Importance importance;

    @Getter
    @Setter
    private LocalDateTime timestamp;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}
