package edu.virginia.cs.musiclocation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final long LOCATION_UPDATE_MIN_TIME = 100L;
    private static final float LOCATION_UPDATE_MIN_DIST = 1.0f;

    private TextView returnValue;
    private EditText editValue;
    private Button submitButton;
    private TextView locationLabel;
    private Button localButton;
    private String Stream_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new downloadClass().execute();

        submitButton = (Button) findViewById(R.id.returnInput);
        returnValue = (TextView) findViewById(R.id.returned);
        editValue = (EditText) findViewById(R.id.editText);
        locationLabel = (TextView) findViewById(R.id.loc);
        localButton = (Button) findViewById(R.id.popularLink);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnValue.setText(editValue.getText());
            }
        });

        localButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), PopularActivity.class);
                startActivity(i);
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

    public class downloadClass extends AsyncTask<String, Integer, Integer> {

        @Override
        public Integer doInBackground(String... input) {
            String JSON_URL="http://api.soundcloud.com/tracks/222906446?client_id=a9e1ea232bfff486717273c718914e5b";
            String JSONString="";
            String nextLine="";
            try {
                InputStream stream1=new URL(JSON_URL).openStream();
                InputStreamReader stream2=new InputStreamReader(stream1);
                BufferedReader stream3=new BufferedReader(stream2);
                nextLine=stream3.readLine();
                JSONString=JSONString+nextLine;
            } catch (Exception e) {
                Log.d("ERROR",e.toString());
                Log.d("ERROR","Download Issue");
            }

            JSONObject object1;
            Stream_URL="";
            Log.d("JSON",JSONString);
            try {
                object1=new JSONObject(JSONString);
                Stream_URL=object1.getString("download_url");
                Stream_URL=Stream_URL+"?client_id=a9e1ea232bfff486717273c718914e5b";
            }
            catch (Exception e) {
                Log.d("ERROR","JSON Issue");
            }

            return 0;
        }

        @Override
        public void onPostExecute(Integer input) {
            MediaPlayer mp=new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                Log.d("JSON",Stream_URL);
                mp.setDataSource(Stream_URL);
                mp.prepare();
                mp.start();
            }
            catch (Exception e) {
                Log.d("ERROR",e.toString());
                Log.d("ERROR","Stream Issue");
            }
        }
    }
}
