package edu.virginia.cs.musiclocation;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class PopularActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_activity);
        ArrayList<String> topSongs=new ArrayList<String>();
        topSongs.add("Test1");
        topSongs.add("Test2");

        ArrayAdapter<String> listA=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listA.addAll(topSongs);
        setListAdapter(listA);
    }

}
