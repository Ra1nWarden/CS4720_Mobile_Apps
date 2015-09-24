package edu.virginia.cs.musiclocation;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.MediaController;

/**
 * A music player that plays music from soundcloud.
 */
public final class OnlineMusicPlayerActivity extends Activity implements MediaController
        .MediaPlayerControl, MediaPlayer.OnPreparedListener {

    // TODO: This track id could be randomly generated.
    static final String TRACK_URL_KEY = "track_url_key";

    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private ImageView albumCover;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.music_player_activity);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(this);

        albumCover = (ImageView) findViewById(R.id.album_cover);

        AudioManager audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);

        String trackURL = getIntent().getStringExtra(TRACK_URL_KEY);

        new DownloadMusicTask(mediaPlayer, albumCover).execute(trackURL);
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaController.hide();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.main_audio_view));

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });

    }
}
