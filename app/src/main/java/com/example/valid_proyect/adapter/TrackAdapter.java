package com.example.valid_proyect.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valid_proyect.R;
import com.example.valid_proyect.models.PojoTracks;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> implements Filterable {

    private List<PojoTracks> trackList;
    private List<PojoTracks> tracksListFull;
    Context context;
    String urls = null;

    public TrackAdapter(Context context, List<PojoTracks> trackList) {
        this.trackList = trackList;
        tracksListFull = new ArrayList<>(trackList);
        this.context = context;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrackViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, final int position) {
        holder.setData(trackList.get(position));
        holder.txtNombre.setText(trackList.get(position).name);
        holder.url.setText(trackList.get(position).url);
        holder.views.setText(trackList.get(position).duration);
        holder.listenme.setText(trackList.get(position).listeners);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urls = trackList.get(position).url;
                if (urls!=null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls));
                    context.startActivity(intent);
                }
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

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PojoTracks> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(tracksListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (PojoTracks item : tracksListFull) {
                    if (item.name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            trackList.clear();
            trackList.addAll((List)results.values);
            notifyDataSetChanged();
        }

    };

    public class TrackViewHolder extends RecyclerView.ViewHolder {

        ImageView star;
        TextView txtNombre, listenme, views, url;
        PojoTracks item;
        View layout;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            star = itemView.findViewById(R.id.star);
            txtNombre = itemView.findViewById(R.id.txtNameArtist);
            listenme = itemView.findViewById(R.id.txtListen);
            views = itemView.findViewById(R.id.txtViews);
            url = itemView.findViewById(R.id.urlview);
        }

        public void setData(PojoTracks item) {

            this.item = item;
            Picasso.with(context).load(item.image.get(0).text).into(star);
        }
    }

    public void swap(List<PojoTracks> newList){
        trackList.clear();
        trackList.addAll(newList);
        notifyDataSetChanged();
    }
}
