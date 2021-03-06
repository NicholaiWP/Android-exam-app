package com.example.nicholai.examdiaryapp;

/**
 * Class for pure text notes, with a title and text body
 */
public class DiaryPage {

    public String title;
    public String pageBody;

    public DiaryPage(){
   //  // Default constructor required for saving data to firebase realtime database
    }

    public DiaryPage(String title, String noteBody) {
        this.title = title;
        this.pageBody = noteBody;
    }


    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getNoteBody() {
        return pageBody;
    }

    public void setNoteBody(String noteBody) {
        this.pageBody = noteBody;
    }

    @Override
    public String toString() {
        return title + " " + "title " + " " + "notebody " + pageBody;
    }
}
