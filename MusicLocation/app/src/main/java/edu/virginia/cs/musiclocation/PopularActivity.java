package edu.virginia.cs.musiclocation;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;

public final class PopularActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_activity);
        String database_path = getApplicationInfo().dataDir + "/musicData.db";
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(database_path, null);
        Cursor cursor = database.rawQuery("SELECT Id as _id, Ref FROM songList", null);
        if (cursor.moveToFirst()) {
            String columns[] = {"Ref"};
            int to[] = {R.id.song_id};
            setListAdapter(new SimpleCursorAdapter(this, R.layout.song_list_entry, cursor, columns,
                    to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));
        }
    }

}
