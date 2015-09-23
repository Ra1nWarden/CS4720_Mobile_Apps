package edu.virginia.cs.musiclocation;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;

/**
 * Created by zihao on 9/22/15.
 */
public class OnlineMusicPlayerActivity extends Activity {

    private MediaPlayer mp;
    private MediaController music_player;
    private ImageView albumCover;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.music_player_activity);

        mp = new MediaPlayer();
        albumCover = (ImageView) findViewById(R.id.album_cover);
        music_player = (MediaController) findViewById(R.id.music_controls);
        music_player.setMediaPlayer(new MediaController.MediaPlayerControl() {
            @Override
            public boolean canSeekBackward() {
                return false;
            }

            @Override
            public boolean canPause() {
                return true;
            }

            @Override
            public int getBufferPercentage() {
                return 0;
            }

            @Override
            public boolean canSeekForward() {
                return false;
            }

            @Override
            public int getAudioSessionId() {
                return 0;
            }

            @Override
            public void start() {

            }

            @Override
            public void pause() {

            }

            @Override
            public int getDuration() {
                return 0;
            }

            @Override
            public int getCurrentPosition() {
                return 0;
            }

            @Override
            public void seekTo(int pos) {

            }

            @Override
            public boolean isPlaying() {
                return true;
            }

        });

        AudioManager audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);

        new DownloadMusicTask(mp, albumCover).execute("222906446");

    }
}
