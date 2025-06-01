package com.blazejdrozd.notes;

import com.blazejdrozd.notes.model.Note;

import java.util.List;

public interface INoteService {
    List<Note> listOfNotes();
    void addNote(Note note);
}