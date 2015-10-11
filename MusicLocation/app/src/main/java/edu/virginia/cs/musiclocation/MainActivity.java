package edu.virginia.cs.musiclocation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public final class MainActivity extends AppCompatActivity {

    private static final long LOCATION_UPDATE_MIN_TIME = 100L;
    private static final float LOCATION_UPDATE_MIN_DIST = 1.0f;

    private TextView locationLabel;
    private TextView nameLabel;
    private Button localButton;
    private Button onlineMusicPlayerButton;
    private SQLiteDatabase database;
    private String database_path;
    //private Button staticMusicPlayerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        nameLabel = (TextView) findViewById(R.id.name);
        locationLabel = (TextView) findViewById(R.id.loc);
        localButton = (Button) findViewById(R.id.popularLink);
        onlineMusicPlayerButton = (Button) findViewById(R.id.onlineMusicPlayerLink);
        //staticMusicPlayerButton = (Button) findViewById(R.id.staticMusicPlayerLink);

        //Initialize Database
        database_path = getApplicationInfo().dataDir + "/musicData.db";
        database = SQLiteDatabase.openOrCreateDatabase(database_path, null);

        //Fill out name
        SQLiteCursor meta_c = (SQLiteCursor) database.rawQuery("SELECT * FROM meta", null);
        if (meta_c.getCount() > 0) {
            meta_c.moveToFirst();
            nameLabel.setText(meta_c.getString(meta_c.getColumnIndex("Name")) + "'s Music Player");
        } else {
            database.execSQL("INSERT INTO meta VALUES (\"\")");
        }

        localButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PopularActivity.class);
                startActivity(i);
            }
        });

        onlineMusicPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OnlineMusicPlayerActivity.class);
                i.putExtra(OnlineMusicPlayerActivity.TRACK_URL_KEY, "222906446");
                startActivity(i);
            }
        });

//        staticMusicPlayerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, StaticMusicPlayerActivity.class);
//                startActivity(i);
//            }
//        });

        setUpLocationManager();
    }

    private void setUpLocationManager() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationLabel.setText("Lat: " + Double.toString(location.getLatitude()) + ",\n"
                        + "Long: " + Double.toString(location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, 0, 0) >= 0) {
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DIST, locationListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
