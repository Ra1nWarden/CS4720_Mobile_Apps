package edu.virginia.cs.musiclocation;

import android.content.ContentValues;
import android.content.Intent;
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

import java.io.File;

public class StartActivity extends AppCompatActivity {

    private Button submitButton;
    private TextView returnValue;
    private EditText editValue;
    private SQLiteDatabase database;
    private String database_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        submitButton = (Button) findViewById(R.id.returnInput);
        returnValue = (TextView) findViewById(R.id.returned);
        editValue = (EditText) findViewById(R.id.editText);

        //Initialize Database
        database_path=getApplicationInfo().dataDir+"/musicData.db";
        File dbCheck=new File(database_path);
        if (dbCheck.exists()) {
            Intent i = new Intent(StartActivity.this, MainActivity.class);
            startActivity(i);
        }

        database= SQLiteDatabase.openOrCreateDatabase(database_path, null);
        //This is the table for metadata (such as name)
        database.execSQL("CREATE TABLE IF NOT EXISTS meta("+
                "Name VARCHAR(100)"+
                ")");
        //This is the table for a local list of songs
        database.execSQL("CREATE TABLE IF NOT EXISTS songList(" +
                "ID INTEGER," +
                "Ref VARCHAR(12)" +
                ")");
        //This is the table for each grid quad you enter (supports up to 999 top songs)
        database.execSQL("CREATE TABLE IF NOT EXISTS GPSList(" +
                "LONG DOUBLE," +
                "LAT DOUBLE," +
                "Ref VARCHAR(320)" +
                ")");

        SQLiteCursor meta_c=(SQLiteCursor)database.rawQuery("SELECT * FROM meta",null);
        if (meta_c.getCount()>0) {
            meta_c.moveToFirst();
        }
        else {
            database.execSQL("INSERT INTO meta VALUES (\"\")");
        }
        meta_c.close();

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
