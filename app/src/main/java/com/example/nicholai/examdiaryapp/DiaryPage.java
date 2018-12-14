package com.example.nicholai.examdiaryapp;

/**
 * Class for pure text notes, with a title and text body
 */
public class DiaryPage {

    private String title;
    private String pageBody;
    //TODO: add image functionality to a diary page (other class is needed)
    // private int image;

    public DiaryPage(){
   //  // Default constructor required for saving data to firebase realtime database
    }

    public DiaryPage(String title, String noteBody) {
        this.title = title;
        this.pageBody = noteBody;
        // this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteBody() {
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
