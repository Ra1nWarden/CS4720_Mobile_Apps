package edu.virginia.cs.musiclocation;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";

    private Button submitButton;
    private EditText editValue;
    private SQLiteDatabase database;
    private String database_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        submitButton = (Button) findViewById(R.id.returnInput);
        editValue = (EditText) findViewById(R.id.editText);

        //Initialize Database
        database_path = getApplicationInfo().dataDir + "/musicData.db";
        File dbCheck = new File(database_path);
        if (dbCheck.exists()) {
            Intent i = new Intent(StartActivity.this, MainActivity.class);
            startActivity(i);
        }
        else {

            database = SQLiteDatabase.openOrCreateDatabase(database_path, null);
            //This is the table for metadata (such as name)
            database.execSQL("CREATE TABLE IF NOT EXISTS meta(" +
                    "Name VARCHAR(100)" +
                    ")");
            //This is the table for a local list of songs
            database.execSQL("CREATE TABLE IF NOT EXISTS songList(" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Ref VARCHAR(12)," +
                    "Count INTEGER" +
                    ")");
            //This is the table for each grid quad you enter (supports up to 999 top songs)
            database.execSQL("CREATE TABLE IF NOT EXISTS GPSList(" +
                    "LONG DOUBLE," +
                    "LAT DOUBLE," +
                    "Ref VARCHAR(320)" +
                    ")");

            SQLiteCursor meta_c = (SQLiteCursor) database.rawQuery("SELECT * FROM meta", null);
            if (meta_c.getCount() > 0) {
                meta_c.moveToFirst();
            } else {
                database.execSQL("INSERT INTO meta VALUES (\"\")");
            }
            meta_c.close();

            try {
                AssetManager manager = getAssets();
                InputStream inputStream = manager.open("static_songs.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine()) != null) {
                    ContentValues row = new ContentValues();
                    row.put("Ref", line);
                    row.put("Count", 0);
                    Log.d(TAG, "inserting row " + row);
                    database.insert("songList", null, row);
                }
            } catch (IOException e) {
                if (Log.isLoggable(TAG, Log.ERROR)) {
                    Log.e(TAG, "Error opening file.", e);
                }
            }
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("name", editValue.getText().toString());
                database.update("meta", cv, null, null);
                database.close();
                Intent i = new Intent(StartActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

}
