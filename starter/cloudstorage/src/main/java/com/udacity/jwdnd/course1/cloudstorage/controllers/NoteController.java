package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping(value = {"/notes"})
    public String insertNote(Authentication authentication, Note note, Model model) {

        String errorMessage;
        int rowsAffected;

        if (note.getNoteid() != null) {
            rowsAffected = noteService.updateNote(note);
        }
        else {
            note.setUserid(userService.getUser(authentication.getName()).getUserid());
            rowsAffected = noteService.insertNote(note);
        }

        if (rowsAffected <= 0) {
            errorMessage = "An unexpected error occurred. Please try again later.";
            model.addAttribute("errorMessage", errorMessage);
            return "result";
        }

        return "result";
    }

    @GetMapping("/notes/delete/{noteid}")
    public String deleteNote(@PathVariable Integer noteid) {
        noteService.deleteNote(noteid);
        return "result";
    }

}