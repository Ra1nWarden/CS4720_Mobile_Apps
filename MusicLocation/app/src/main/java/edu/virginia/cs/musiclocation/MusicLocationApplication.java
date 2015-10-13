package edu.virginia.cs.musiclocation;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * An application class.
 */
public final class MusicLocationApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Song.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Y4bijYndUvKa8xtvb5aFXsXffwwHuW0jhMYybK25",
                "lujWLXCF1lAh28pGqQFls9d3CmUpjEzKCmCcNwoC");
    }
}
