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

import java.util.ArrayList;

public final class PopularActivity extends ListActivity {

    @Override
    protected void onResume() {
        super.onResume();
        String database_path = getApplicationInfo().dataDir + "/musicData.db";
        SQLiteDatabase database2 = SQLiteDatabase.openOrCreateDatabase(database_path, null);
        Cursor cursor = database2.rawQuery("SELECT Id as _id, Ref, Count FROM songList", null);
        ArrayList<String> list_of_songs=new ArrayList<String>();
        int current_count=0;
        cursor.moveToFirst();
        while (current_count<cursor.getCount()) {
            list_of_songs.add(cursor.getString(cursor.getColumnIndex("Ref"))+", Plays: "+cursor.getInt(cursor.getColumnIndex("Count")));
            cursor.moveToNext();
            current_count++;
        }
        Log.d("ERROR",Integer.toString(list_of_songs.size()));
        Log.d("ERROR",Integer.toString(current_count));

        ArrayAdapter<String> AList=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        AList.addAll(list_of_songs);
        setListAdapter(AList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_activity);


        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView label = (TextView) view.findViewById(R.id.song_id);
                //CharSequence songId = label.getText();

                String database_path = getApplicationInfo().dataDir + "/musicData.db";
                SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(database_path, null);
                String song=(String)parent.getAdapter().getItem(position);
                String song_cut=song.split(",")[0];
                SQLiteCursor meta_c = (SQLiteCursor) database.rawQuery("SELECT * FROM songList WHERE Ref=" + song_cut, null);

                meta_c.moveToFirst();
                int current_count = meta_c.getInt(meta_c.getColumnIndex("Count"));
                CharSequence songId = meta_c.getString(meta_c.getColumnIndex("Ref"));
                ContentValues tableVals = new ContentValues();
                tableVals.put("Count", current_count+1);

                Log.d("ERROR",Integer.toString(current_count));
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
