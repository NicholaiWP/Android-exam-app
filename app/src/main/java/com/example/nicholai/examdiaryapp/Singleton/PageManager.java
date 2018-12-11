package com.example.nicholai.examdiaryapp.Singleton;

import com.example.nicholai.examdiaryapp.DiaryPage;

import java.util.ArrayList;

/**
 * Class used for storing and accessing note data across fragments
 */
public class PageManager {
    private static final PageManager ourInstance = new PageManager();

    public static String PAGE_PATH = "pages";


    public ArrayList<DiaryPage> pages = new ArrayList<>();


    public static PageManager getInstance() {
        return ourInstance;
    }

    private PageManager() {
    }

}
