package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note) {
        return this.noteMapper.insert(note);
    }

    public Note getNote(Integer noteId) {
        return this.noteMapper.getNote(noteId);
    }

    public int editNote(Note note) {
        return this.noteMapper.update(note.getNoteTitle(), note.getNoteDescription(), note.getNoteId());
    }

    public List<Note> getAllNotes(Integer userId) {
        return this.noteMapper.getAllNotes(userId);
    }

    public int deleteNote(Integer noteId) {
        return this.noteMapper.delete(noteId);
    }

}
