package edu.virginia.cs.musiclocation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

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

    private String Stream_URL;
    private String Image_URL;
    private Bitmap imageCoverArt;

    private final MediaPlayer player;
    private final ImageView albumCover;

    public DownloadMusicTask(MediaPlayer player, ImageView albumCover) {
        this.player = player;
        this.albumCover = albumCover;
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
            Stream_URL = parsedJSON.getString(STREAM_KEY);
            Stream_URL = Stream_URL + CLIENT_ID_URL;
            Image_URL = parsedJSON.getString(ARTWORK_KEY);
            Image_URL = Image_URL + CLIENT_ID_URL;
            InputStream image_stream = new URL(Image_URL).openStream();
            imageCoverArt = BitmapFactory.decodeStream(image_stream);
            image_stream.close();

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
            player.setDataSource(Stream_URL);
            player.prepare();
            player.start();
        } catch (Exception e) {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Error in setting stream URL. ", e);
            }
        }

        try {
            if (imageCoverArt != null) {
                albumCover.setImageBitmap(imageCoverArt);
            }
        } catch (Exception e) {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Unable to set album cover. ", e);
            }
        }
    }
}
