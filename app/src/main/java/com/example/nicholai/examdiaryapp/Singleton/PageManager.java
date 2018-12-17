package com.example.nicholai.examdiaryapp.Singleton;

import com.example.nicholai.examdiaryapp.DiaryPage;

import java.util.ArrayList;

/**
 * Class used for storing and accessing note data across fragments
 */
public class PageManager {
    private static final PageManager ourInstance = new PageManager();

    //this string is used by the database's child reference. Every diary note is listed under the name of this string in
    //the database
    public static String PAGE_PATH = "pages";

//stores pages created by the user. This is kept in memory as the singleton uses a static approach to access other classes/activities.
    public ArrayList<DiaryPage> pages = new ArrayList<>();


    public static PageManager getInstance() {
        return ourInstance;
    }

    private PageManager() {
    }

}
