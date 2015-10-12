package edu.virginia.cs.musiclocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {
    private Context r_context;
    private ArrayList<Song> data;

    public SongAdapter(Context c, int r, ArrayList<Song> d) {
        super(c, r, d);
        r_context=c;
        data=d;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View output=convertView;
        if (convertView==null) {
            LayoutInflater li=(LayoutInflater) r_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            output=li.inflate(R.layout.song_layout, null, true);
        }

        TextView nameView=(TextView) output.findViewById(R.id.song_name);
        TextView popView=(TextView) output.findViewById(R.id.song_pop);
        TextView playsView=(TextView) output.findViewById(R.id.song_plays);

        nameView.setText(data.get(position).getName());
        playsView.setText(Integer.toString(data.get(position).getPlays()));

        return output;
    }

    @Override
    public Song getItem(int position) {
        return data.get(position);
    }
}