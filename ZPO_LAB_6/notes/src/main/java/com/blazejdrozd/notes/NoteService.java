package com.blazejdrozd.notes;

import com.blazejdrozd.notes.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepo noteRepo;

    public List<Note> listOfNotes() {
        return noteRepo.findByOrderByTimestampDesc();
    }

    public void addNote(Note note) {
        noteRepo.save(note);
    }

    public void removeNote(Note note) {
        noteRepo.delete(note);
    }
}

