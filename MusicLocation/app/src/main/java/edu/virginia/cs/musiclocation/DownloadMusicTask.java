package edu.virginia.cs.musiclocation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * A task that downloads music from soundcloud.
 */
public final class DownloadMusicTask extends AsyncTask<String, Integer, Void> {

    private static final String TAG = "DownloadMusicTask";
    private static final String SOUND_CLOUD_URL = "http://api.soundcloud" +
            ".com/tracks/";
    private static final String CLIENT_ID_URL = "?client_id=a9e1ea232bfff486717273c718914e5b";

    private static final String STREAM_KEY = "download_url";

    private String streamURL;

    private final MediaPlayer player;

    public DownloadMusicTask(MediaPlayer player, Context c_ref) {
        this.player = player;
    }

    @Override
    public Void doInBackground(String... input) {
        try {
            InputStream stream1 = new URL(SOUND_CLOUD_URL + input[0] + CLIENT_ID_URL).openStream();
            InputStreamReader stream2 = new InputStreamReader(stream1);
            BufferedReader stream3 = new BufferedReader(stream2);
            String JSONString = stream3.readLine();
            stream1.close();
            stream2.close();
            stream3.close();
            JSONObject parsedJSON = new JSONObject(JSONString);
            streamURL = parsedJSON.getString(STREAM_KEY);
            streamURL = streamURL + CLIENT_ID_URL;
        } catch (Exception e) {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Error in decoding json. ", e);
            }
        }
        return null;
    }

    @Override
    public void onPostExecute(Void ret) {
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.setDataSource(streamURL);
            player.prepare();
            player.start();
        } catch (Exception e) {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Error in setting stream URL. ", e);
            }
        }
    }
}
