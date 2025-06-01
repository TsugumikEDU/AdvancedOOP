package com.blazejdrozd.notes;

import com.blazejdrozd.notes.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService implements INoteService {

    @Autowired
    private NoteRepo noteRepo;

    @Override
    public List<Note> listOfNotes() {
        return noteRepo.findByOrderByTimestampDesc();
    }

    @Override
    public void addNote(Note note) {
        noteRepo.save(note);
    }
}

