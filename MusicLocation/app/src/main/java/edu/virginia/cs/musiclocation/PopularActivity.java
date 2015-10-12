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

import com.parse.FindCallback;
import com.parse.ParseException;
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
                //TextView label = (TextView) view.findViewById(R.id.song_id);
                //CharSequence songId = label.getText();

                String database_path = getApplicationInfo().dataDir + "/musicData.db";
                SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(database_path, null);
                String song = (String) parent.getAdapter().getItem(position);
                String song_cut = song.split(",")[0];
                SQLiteCursor meta_c = (SQLiteCursor) database.rawQuery("SELECT * FROM songList " +
                        "WHERE Ref=" + song_cut, null);

                meta_c.moveToFirst();
                int current_count = meta_c.getInt(meta_c.getColumnIndex("Count"));
                CharSequence songId = meta_c.getString(meta_c.getColumnIndex("Ref"));
                ContentValues tableVals = new ContentValues();
                tableVals.put("Count", current_count + 1);

                Log.d("ERROR", Integer.toString(current_count));
                database.update("songList", tableVals, "Ref=" + song_cut, null);
                meta_c.close();
                database.close();

                Intent i = new Intent(PopularActivity.this, OnlineMusicPlayerActivity.class);
                i.putExtra(OnlineMusicPlayerActivity.TRACK_URL_KEY, songId);
                startActivity(i);
            }
        });
    }

}
