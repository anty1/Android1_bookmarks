package com.test_apps.bookmarks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BookmarkDetailsActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnSave, btnDelete, btnClose;
    EditText editTextName, editTextUrl;
    private int _Bookmark_Id=0;
    private String _creator="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_details);

        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
        findViewById(R.id.btnClose).setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextUrl = (EditText) findViewById(R.id.editTextUrl);


        _Bookmark_Id =0;
        Intent intent = getIntent();
        _Bookmark_Id =intent.getIntExtra("bookmark_Id", 0);
        if (_Bookmark_Id == 0) {
            _creator = intent.getStringExtra("creator");
        }
        BookmarkManager bookmarkManager = new BookmarkManager(this);
        //Bookmark bookmark = new Bookmark();
        Bookmark bookmark = bookmarkManager.getBookmarkById(_Bookmark_Id);

        editTextName.setText(bookmark.name);
        editTextUrl.setText(bookmark.url);

    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            BookmarkManager bookmarkManager = new BookmarkManager(this);
            Bookmark bookmark = new Bookmark();
            bookmark.url=editTextUrl.getText().toString();
            bookmark.name=editTextName.getText().toString();
            bookmark.bookmark_ID=_Bookmark_Id;
            bookmark.creator = _creator;

            if (_Bookmark_Id==0){
                _Bookmark_Id = bookmarkManager.insert(bookmark);

                Toast.makeText(this,"New Bookmark Insert",Toast.LENGTH_SHORT).show();
            }else{

                bookmarkManager.update(bookmark);
                Toast.makeText(this,"Bookmark Record updated",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            BookmarkManager bookmarkManager = new BookmarkManager(this);
            bookmarkManager.delete(_Bookmark_Id);
            Toast.makeText(this, "Bookmark Record Deleted", Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();
        }


    }

}
