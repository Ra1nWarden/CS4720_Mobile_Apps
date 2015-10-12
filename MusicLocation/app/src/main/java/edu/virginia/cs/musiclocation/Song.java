package edu.virginia.cs.musiclocation;

import com.parse.ParseObject;

public class Song extends ParseObject {

    private static final String SONG_TITLE_KEY = "songTitle";
    private static final String PLAYS_KEY = "plays";
    private static final String VOTES_KEY = "votes";
    private static final String SOUND_CLOUD_ID_KEY = "soundCloudId";

    public String getSongName() {
        return (String) get(SONG_TITLE_KEY);
    }

    public int getPlays() {
        return (int) get(PLAYS_KEY);
    }

    public int getVotes() {
        return (int) get(VOTES_KEY);
    }

    public int getId() {
        return (int) get(SOUND_CLOUD_ID_KEY);
    }
}