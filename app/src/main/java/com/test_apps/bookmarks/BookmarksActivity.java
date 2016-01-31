package com.test_apps.bookmarks;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class BookmarksActivity extends ListActivity implements android.view.View.OnClickListener {

    Button btnAdd, btnGetAll;
    TextView bookmark_Id;
    private String _username;

    @Override
    public void onClick(View view) {
        if (view== findViewById(R.id.btnAdd)){
            Intent intent = new Intent(this,BookmarkDetailsActivity.class);
            intent.putExtra("bookmark_Id",0);
            intent.putExtra("creator",_username);
            startActivity(intent);

        }
        else if (view== findViewById(R.id.btnCloseBookmarks)){
            finish();
        }
        else {

            final BookmarkManager bookmarkManager = new BookmarkManager(this);

            final ArrayList<HashMap<String, String>> bookmarkList =  bookmarkManager.getBookmarkList(_username);
            if(bookmarkList.size()!=0) {
                ListView lv = getListView();
                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        bookmark_Id = (TextView) view.findViewById(R.id.bookmark_Id);
                        String bookmarkId = bookmark_Id.getText().toString();
                        Intent objIntent = new Intent(getApplicationContext(), BookmarkDetailsActivity.class);
                        objIntent.putExtra("bookmark_Id", Integer.parseInt(bookmarkId));
                        startActivity(objIntent);

                        return true;
                    }
                });

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        bookmark_Id = (TextView) view.findViewById(R.id.bookmark_Id);

                        // Open browser and go to url
                        int book_id = Integer.parseInt(bookmark_Id.getText().toString());
                        String url = bookmarkManager.getBookmarkById(book_id).url;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                        try {
                            startActivity(browserIntent);
                        } catch (android.content.ActivityNotFoundException e) {
                            Toast.makeText(view.getContext(), "Wrong Bookmark URL!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                ListAdapter adapter = new SimpleAdapter( BookmarksActivity.this,bookmarkList, R.layout.view_bookmark_entry, new String[] { "id","name"}, new int[] {R.id.bookmark_Id, R.id.bookmark_name});
                setListAdapter(adapter);
            }else{
                Toast.makeText(this,"No bookmark!",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);
        findViewById(R.id.btnCloseBookmarks).setOnClickListener(this);

        Intent intent = getIntent();
        _username = intent.getStringExtra("username");
    }

}
