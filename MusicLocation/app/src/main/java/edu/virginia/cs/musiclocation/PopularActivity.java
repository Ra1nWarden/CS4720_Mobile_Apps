package edu.virginia.cs.musiclocation;

import android.app.ListActivity;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;

public class PopularActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        ArrayList<String> topSongs=new ArrayList<String>();
        topSongs.add("Test1");
        topSongs.add("Test2");

        ArrayAdapter<String> listA=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listA.addAll(topSongs);
        setListAdapter(listA);
    }

}
