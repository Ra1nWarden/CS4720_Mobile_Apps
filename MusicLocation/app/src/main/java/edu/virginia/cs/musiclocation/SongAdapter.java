package edu.virginia.cs.musiclocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SongAdapter extends ArrayAdapter<Song> {
    private Context context;

    public SongAdapter(Context context, int r) {
        super(context, r);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View output = convertView;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            output = li.inflate(R.layout.song_layout, null, true);
        }

        TextView nameView = (TextView) output.findViewById(R.id.song_name);
        TextView popView = (TextView) output.findViewById(R.id.song_pop);
        TextView playsView = (TextView) output.findViewById(R.id.song_plays);

        nameView.setText(getItem(position).getSongName());
        playsView.setText(Integer.toString(getItem(position).getPlays()));
        popView.setText(Integer.toString(getItem(position).getVotes()));

        return output;
    }

}