package com.test_apps.bookmarks;

/**
 * Created by asimic on 1/30/16.
 */
public class Bookmark {
    // Labels table name
    public static final String TABLE = "Bookmark";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_url = "url";
    public static final String KEY_creator = "creator";

    // property help us to keep data
    public int bookmark_ID;
    public String name;
    public String url;
    public String creator;
}
