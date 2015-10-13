package edu.virginia.cs.musiclocation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public final class PopularActivity extends Activity {

    private static final double THRESHOLD = 1.0;
    private static final String TAG = "PopularActivity";
    static final String LATITUDE_KEY = "latitude";
    static final String LONGITUDE_KEY = "longitude";

    private SongAdapter adapter;
    private ListView listView;
    private double latitude;
    private double longitude;

    private static double dist(double lat1, double lat2, double lon1, double lon2) {
        return Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter.isEmpty()) {
            ParseQuery.getQuery(Song.class).findInBackground(new FindCallback<Song>() {
                @Override
                public void done(List<Song> objects, ParseException e) {
                    if (e == null) {
                        for (Song each : objects) {
                            double lat = each.getLatitude();
                            double lon = each.getLongitude();
                            double diff = dist(lat, latitude, lon, longitude);
                            if (diff < THRESHOLD) {
                                adapter.add(each);
                            }
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_activity);
        listView = (ListView) findViewById(R.id.list);
        LinearLayout titleView = (LinearLayout) getLayoutInflater().inflate(R.layout
                .list_title_view, null);
        listView.addHeaderView(titleView);
        adapter = new SongAdapter(this, R.layout.song_layout);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = adapter.getItem(position - 1);
                song.incrementPlay();
                Intent i = new Intent(PopularActivity.this, OnlineMusicPlayerActivity.class);
                i.putExtra(OnlineMusicPlayerActivity.PARSE_OBJECT_ID, adapter.getItem(position - 1)
                        .getObjectId());
                startActivity(i);
            }
        });

        latitude = getIntent().getDoubleExtra(LATITUDE_KEY, -1);
        longitude = getIntent().getDoubleExtra(LONGITUDE_KEY, -1);
    }

}

