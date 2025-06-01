package com.blazejdrozd.notes;

import com.blazejdrozd.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Long> {
    List<Note> findByOrderByTimestampDesc();
}
