package edu.virginia.cs.musiclocation;

import android.content.Context;
import android.content.res.Resources;
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

    private static final String ARTWORK_KEY = "artwork_url";
    private static final String STREAM_KEY = "download_url";
    private static final String USER_KEY = "user";
    private static final String ARTIST_KEY = "username";
    private static final String TITLE_KEY = "title";

    private String streamURL;
    private String imageURL;
    private String artist;
    private String title;
    private Bitmap imageCoverArt;

    private final MediaPlayer player;
    private final ImageView albumCover;
    private final TextView titleText;
    private final TextView artistText;

    private Drawable no_art;
    private boolean found_art;

    public DownloadMusicTask(MediaPlayer player, ImageView albumCover, TextView titleView, TextView artistView, Context c_ref) {
        this.player = player;
        this.albumCover = albumCover;
        this.titleText = titleView;
        this.artistText = artistView;
        found_art=false;
        no_art=ContextCompat.getDrawable(c_ref,R.drawable.no_find_art);
    }

    @Override
    public Void doInBackground(String... input) {
        found_art=false;
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
            imageURL = parsedJSON.getString(ARTWORK_KEY);
            imageURL = imageURL + CLIENT_ID_URL;
            artist=parsedJSON.getJSONObject(USER_KEY).getString(ARTIST_KEY);
            title=parsedJSON.getString(TITLE_KEY);

            //On some networks, this might not work correctly
            Log.d("DOWNLOAD",imageURL);
            InputStream image_stream = new URL(imageURL).openStream();
            imageCoverArt = BitmapFactory.decodeStream(image_stream);
            found_art=true;
            image_stream.close();
        } catch (Exception e) {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Error in decoding json. ", e);
            }
            imageCoverArt=null;
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

        try {
            if (imageCoverArt != null) {
                if (found_art) {
                    albumCover.setImageBitmap(imageCoverArt);
                } else {
                    albumCover.setImageDrawable(no_art);
                }
            }
            titleText.setText(title);
            artistText.setText(artist);
        } catch (Exception e) {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Unable to set album cover. ", e);
            }
        }
    }
}
