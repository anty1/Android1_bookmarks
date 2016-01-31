package com.test_apps.bookmarks;

/**
 * Created by asimic on 1/30/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class BookmarkManager {
    private DBHelper dbHelper;

    public BookmarkManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Bookmark bookmark) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Bookmark.KEY_url,bookmark.url);
        values.put(Bookmark.KEY_name, bookmark.name);
        values.put(Bookmark.KEY_creator, bookmark.creator);

        // Inserting Row
        long bookmark_Id = db.insert(Bookmark.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) bookmark_Id;
    }

    public void delete(int bookmark_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Bookmark.TABLE, Bookmark.KEY_ID + "= ?", new String[] { String.valueOf(bookmark_Id) });
        db.close(); // Closing database connection
    }

    public void update(Bookmark bookmark) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Bookmark.KEY_url,bookmark.url);
        values.put(Bookmark.KEY_name, bookmark.name);

        db.update(Bookmark.TABLE, values, Bookmark.KEY_ID + "= ?", new String[] { String.valueOf(bookmark.bookmark_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getBookmarkList(String user) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Bookmark.KEY_ID + "," +
                Bookmark.KEY_name + "," +
                Bookmark.KEY_url +
                " FROM " + Bookmark.TABLE + " WHERE " + Bookmark.KEY_creator + " ='" + user + "' order by " + Bookmark.KEY_name;

        ArrayList<HashMap<String, String>> bookmarkList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> bookmark = new HashMap<String, String>();
                bookmark.put("id", cursor.getString(cursor.getColumnIndex(Bookmark.KEY_ID)));
                bookmark.put("name", cursor.getString(cursor.getColumnIndex(Bookmark.KEY_name)));
                bookmarkList.add(bookmark);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookmarkList;

    }

    public Bookmark getBookmarkById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Bookmark.KEY_ID + "," +
                Bookmark.KEY_name + "," +
                Bookmark.KEY_url +
                " FROM " + Bookmark.TABLE
                + " WHERE " +
                Bookmark.KEY_ID + "=?";

        int iCount =0;
        Bookmark bookmark = new Bookmark();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                bookmark.bookmark_ID =cursor.getInt(cursor.getColumnIndex(Bookmark.KEY_ID));
                bookmark.name =cursor.getString(cursor.getColumnIndex(Bookmark.KEY_name));
                bookmark.url  =cursor.getString(cursor.getColumnIndex(Bookmark.KEY_url));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookmark;
    }
}
