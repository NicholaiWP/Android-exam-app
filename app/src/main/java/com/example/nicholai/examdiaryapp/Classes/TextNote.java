package com.example.nicholai.examdiaryapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class TextNote implements Parcelable {


    private String noteTitle = "";

    private String noteBody = "";

    public TextNote(String noteT, String noteBody){
        this.noteTitle = noteT;
        this.noteBody = noteBody;
    }

    protected TextNote(Parcel in) {
        noteTitle = in.readString();
        noteBody = in.readString();
    }

    public static final Creator<TextNote> CREATOR = new Creator<TextNote>() {
        @Override
        public TextNote createFromParcel(Parcel in) {
            return new TextNote(in);
        }

        @Override
        public TextNote[] newArray(int size) {
            return new TextNote[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteTitle);
        dest.writeString(noteBody);
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

}
