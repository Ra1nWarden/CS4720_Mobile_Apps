package edu.virginia.cs.musiclocation;

public class Song {
    private String name;
    private int plays;
    private int popularity;
    private String id;

    public Song(String n, int p) {
        name=n;
        plays=p;
        popularity=0;
    }

    public String getName() {
        return name;
    }

    public int getPlays() {
        return plays;
    }
}