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
import com.example.valid_proyect.models.PojoArtists;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> implements Filterable {

    private List<PojoArtists> artistList;
    private List<PojoArtists> artistsListFull;
    Context context;
    String urls = null;

    public ArtistAdapter(Context context, List<PojoArtists> artistList) {
        this.artistList = artistList;
        artistsListFull = new ArrayList<>(artistList);
        this.context = context;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArtistViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, final int position) {
        holder.setData(artistList.get(position));
        //holder.star.setImageResource(artistList.get(position).image);
        holder.txtNombre.setText(artistList.get(position).name);
        holder.url.setText(artistList.get(position).url);
        holder.views.setText(artistList.get(position).playcount);
        holder.listenme.setText(artistList.get(position).streamable);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urls = artistList.get(position).url;
                if (urls!=null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (artistList != null) {
            return artistList.size();
        }
        return 0;
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PojoArtists> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(artistsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (PojoArtists item : artistsListFull) {
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
            artistList.clear();
            artistList.addAll((List)results.values);
            notifyDataSetChanged();
        }

    };

    public  class ArtistViewHolder extends RecyclerView.ViewHolder{

        ImageView star;
        TextView txtNombre, listenme, views, url;
        PojoArtists item;
        View layout;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView;
            star = itemView.findViewById(R.id.star);
            txtNombre = itemView.findViewById(R.id.txtNameArtist);
            listenme = itemView.findViewById(R.id.txtListen);
            views = itemView.findViewById(R.id.txtViews);
            url = itemView.findViewById(R.id.urlview);

        }

        public void setData(PojoArtists item) {
            this.item = item;
            Picasso.with(context).load(item.image.get(0).text).into(star);
        }

    }

    public void swap(List<PojoArtists> newList){
        artistList.clear();
        artistList.addAll(newList);
        notifyDataSetChanged();
    }
}
