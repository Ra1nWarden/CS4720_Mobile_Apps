package edu.virginia.cs.musiclocation;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Songs")
public class Song extends ParseObject {

    static final String SONG_TITLE_KEY = "songTitle";
    static final String PLAYS_KEY = "plays";
    static final String VOTES_KEY = "votes";
    static final String SOUND_CLOUD_ID_KEY = "soundCloudId";

    public Song() {
    }

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
