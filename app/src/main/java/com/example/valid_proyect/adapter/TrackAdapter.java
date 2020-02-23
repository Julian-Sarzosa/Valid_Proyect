package com.example.valid_proyect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valid_proyect.R;
import com.example.valid_proyect.models.PojoTracks;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder>{

    private List<PojoTracks> trackList;
    Context context;
    IAdaptersRecylcer click;

    public TrackAdapter(Context context, List<PojoTracks> trackList, IAdaptersRecylcer click) {
        this.trackList = trackList;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrackViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, final int position) {
        holder.setData(trackList.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.clickItem(trackList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (trackList != null) {
            return trackList.size();
        }
        return 0;
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {

        ImageView star;
        TextView txtNombre, listenme, views;
        PojoTracks item;
        View layout;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            star = itemView.findViewById(R.id.star);
            txtNombre = itemView.findViewById(R.id.txtNameArtist);
            listenme = itemView.findViewById(R.id.txtListen);
            views = itemView.findViewById(R.id.txtViews);
            //duration =itemView.findViewById(R.id.txtDuration);
        }

        public void setData(PojoTracks item) {

            this.item = item;
            Picasso.with(context).load(item.image.get(0).text).into(star);
            txtNombre.setText(item.name);
            listenme.setText(item.duration);
            views.setText(item.listeners);
        }
    }

    public void swap(List<PojoTracks> newList){
        trackList.clear();
        trackList.addAll(newList);
        notifyDataSetChanged();
    }

    public interface IAdaptersRecylcer{
        void clickItem(PojoTracks item);
    }
}
