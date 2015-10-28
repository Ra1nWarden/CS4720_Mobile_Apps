package edu.virginia.cs.musiclocation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        nameLabel = (TextView) findViewById(R.id.name);
        locationLabel = (TextView) findViewById(R.id.loc);
        localButton = (Button) findViewById(R.id.popularLink);
        onlineMusicPlayerButton = (Button) findViewById(R.id.onlineMusicPlayerLink);

        SharedPreferences preferences = getSharedPreferences(getApplication().getPackageName(),
                MODE_PRIVATE);
        nameLabel.setText(preferences.getString(StartActivity.NAME_KEY, "") + "'s Music Player");

        setUpLocationManager();

        localButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PopularActivity.class);
                i.putExtra(PopularActivity.LATITUDE_KEY, latitude);
                i.putExtra(PopularActivity.LONGITUDE_KEY, longitude);
                startActivity(i);
            }
        });

        onlineMusicPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OnlineMusicPlayerActivity.class);
                startActivity(i);
            }
        });
    }

    private void setUpLocationManager() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
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
    

}
