package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String addOrEditNote(Authentication authentication, Model model, Note note) {
        int userId = this.userService.getUser(authentication.getName()).getUserId();
        int result;
        if (note.getNoteId() != null) {
            result = noteService.editNote(note);
        }
        else {
            note.setUserId(userId);
            result = this.noteService.addNote(note);
        }
        model.addAttribute("saved", result > 0);
        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Model model) {
        int result = noteService.deleteNote(noteId);
        model.addAttribute("saved", result > 0);
        return "result";
    }
}
