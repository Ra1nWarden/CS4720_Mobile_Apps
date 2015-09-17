package edu.virginia.cs.musiclocation;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final long LOCATION_UPDATE_MIN_TIME = 100L;
    private static final float LOCATION_UPDATE_MIN_DIST = 1.0f;

    private TextView returnValue;
    private EditText editValue;
    private Button submitButton;
    private TextView locationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = (Button) findViewById(R.id.returnInput);
        returnValue = (TextView) findViewById(R.id.returned);
        editValue = (EditText) findViewById(R.id.editText);
        locationLabel = (TextView) findViewById(R.id.loc);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnValue.setText(editValue.getText());
            }
        });

        setUpLocationManager();
    }

    private void setUpLocationManager() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationLabel.setText(Double.toString(location.getLatitude()) + ","
                        + Double.toString(location.getLongitude()));
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
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
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
