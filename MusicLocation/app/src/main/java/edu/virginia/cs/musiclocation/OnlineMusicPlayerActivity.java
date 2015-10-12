package edu.virginia.cs.musiclocation;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

/**
 * A music player that plays music from soundcloud.
 */
public final class OnlineMusicPlayerActivity extends Activity implements MediaController
        .MediaPlayerControl, MediaPlayer.OnPreparedListener, SensorEventListener {

    // TODO: This track id could be randomly generated.
    static final String TRACK_URL_KEY = "track_url_key";

    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private ImageView albumCover;
    private TextView titleText;
    private TextView artistText;


    //Sensor
    private double threshold;
    private long threshold_time=0;
    private Sensor accel;
    private SensorManager sm;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.music_player_activity);

        threshold=15.0;

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setVolume(.5f, .5f);
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(this);

        albumCover = (ImageView) findViewById(R.id.album_cover);
        titleText=(TextView) findViewById(R.id.song_title);
        artistText=(TextView) findViewById(R.id.song_artist);

        AudioManager audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)/3, 0);

        String trackURL = getIntent().getStringExtra(TRACK_URL_KEY);

        sm=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        new DownloadMusicTask(mediaPlayer, albumCover, titleText, artistText, this).execute(trackURL);
    }

    @Override
    public void onStop() {
        super.onStop();
        //mediaController.hide();
        mediaPlayer.pause();
        //mediaPlayer.release();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mediaController.show();
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
        mediaPlayer.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }


    @Override()
    public void start() {mediaPlayer.start();}

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

    //Sensor Methods
    @Override
    public void onAccuracyChanged(Sensor i, int acc) {

    }

    @Override
    public void onSensorChanged(SensorEvent i) {
        double mag=Math.sqrt(Math.pow(i.values[0],2)+Math.pow(i.values[1],2)+Math.pow(i.values[2],2));
        if (mag>threshold) {
            if (System.currentTimeMillis()-threshold_time>1500) {
                threshold_time=System.currentTimeMillis();
                seekTo(getCurrentPosition()+5000);
            }
        }
    }
}
