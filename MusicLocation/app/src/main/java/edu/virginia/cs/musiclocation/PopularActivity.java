package edu.virginia.cs.musiclocation;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public final class PopularActivity extends ListActivity {

    private static final String TAG = "PopularActivity";

    private SongAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        ParseQuery.getQuery(Song.class).findInBackground(new FindCallback<Song>() {
            @Override
            public void done(List<Song> objects, ParseException e) {
                if (e == null) {
                    for (Song each : objects) {
                        adapter.add(each);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    if (Log.isLoggable(TAG, Log.ERROR)) {
                        Log.e(TAG, "Query parse database error. ", e);
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_activity);
        adapter = new SongAdapter(this, R.layout.song_layout);
        setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(PopularActivity.this, OnlineMusicPlayerActivity.class);
                i.putExtra(OnlineMusicPlayerActivity.PARSE_OBJECT_ID, adapter.getItem(position)
                        .getObjectId());
                startActivity(i);
            }
        });
    }

}

