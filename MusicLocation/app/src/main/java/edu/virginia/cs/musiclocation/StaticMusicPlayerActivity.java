package edu.virginia.cs.musiclocation;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

/**
 * A class that plays static music.
 */
public class StaticMusicPlayerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        AudioManager audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);

        MediaPlayer player = MediaPlayer.create(this, R.raw.supermassive_black_hole);
        player.start();
    }
}
