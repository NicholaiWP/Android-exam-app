package com.example.nicholai.examdiaryapp.Singleton;

import com.example.nicholai.examdiaryapp.Note;
import java.util.ArrayList;

/**
 * Class used for storing and accessing note data across fragments
 */
public class NoteManager {
    private static final NoteManager ourInstance = new NoteManager();

    public static String NOTE_PATH = "notes";

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public ArrayList<Note> notes = new ArrayList<>();


    public static NoteManager getInstance() {
        return ourInstance;
    }

    private NoteManager() {
    }

}
