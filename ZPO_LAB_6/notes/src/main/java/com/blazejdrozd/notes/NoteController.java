package com.blazejdrozd.notes;

import com.blazejdrozd.notes.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/list")
    public String listNotes(Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("notes", noteService.listOfNotes());
        return "notes";
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute Note note) {
        noteService.addNote(note);
        return "redirect:/list";
    }

    @PostMapping("/remove")
    public String removeNote(@ModelAttribute Note note) {
        noteService.removeNote(note);
        return "redirect:/list";
    }
}
