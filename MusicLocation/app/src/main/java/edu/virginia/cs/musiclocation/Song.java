package edu.virginia.cs.musiclocation;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Songs")
public final class Song extends ParseObject {

    static final String SONG_TITLE_KEY = "songTitle";
    static final String PLAYS_KEY = "plays";
    static final String VOTES_KEY = "votes";
    static final String SOUND_CLOUD_ID_KEY = "soundCloudId";
    static final String ARTIST_NAME_KEY = "artistName";
    static final String COVER_KEY = "coverPicture";
    static final String LATITUDE_KEY = "latitude";
    static final String LONGITUDE_KEY = "longitude";

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

    public String getArtistName() {
        return (String) get(ARTIST_NAME_KEY);
    }

    public ParseFile getCoverFile() {
        return (ParseFile) get(COVER_KEY);
    }

    public void incrementPlay() {
        increment(PLAYS_KEY);
        saveInBackground();
    }

    public void voteUp() {
        increment(VOTES_KEY);
        saveInBackground();
    }

    public void voteDown() {
        increment(VOTES_KEY, -1);
        saveInBackground();
    }

    public double getLatitude() {
        return getDouble(LATITUDE_KEY);
    }

    public double getLongitude() {
        return getDouble(LONGITUDE_KEY);
    }
}
