package edu.virginia.cs.musiclocation;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * An application class.
 */
public class MusicLocationApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Song.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "w9jkBo7rPSAK6mld5HTLCndQhCoY6g0BgLD8Cwvi",
                "9243kDKt4VVCzfzsubvfUHmSNBF0NTLKmx4tvpJn");
    }
}
