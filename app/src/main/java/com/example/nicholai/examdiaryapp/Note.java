package com.example.nicholai.examdiaryapp;

/**
 * Class for pure text notes
 */
public class Note {

    private String title;
    private String noteBody;
    // private int image;

    public Note(){
   //  // Default constructor required for saving data to firebase realtime database
    }

    public Note(String title, String noteBody) {
        this.title = title;
        this.noteBody = noteBody;
        // this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    @Override
    public String toString() {
        return title + " " + "title " + " " + "notebody " + noteBody;
    }
}
