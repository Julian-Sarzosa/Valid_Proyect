package com.example.valid_proyect.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valid_proyect.R;
import com.example.valid_proyect.database.ArtistsSql;
import com.example.valid_proyect.fragments.Artist;
import com.example.valid_proyect.models.PojoArtists;
import com.example.valid_proyect.utils.Contants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>{

    private List<PojoArtists> artistList;
    Context context;
    String urls = null;

    public ArtistAdapter(Context context, List<PojoArtists> artistList) {
        this.artistList = artistList;
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
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (urls!=null) {
                    urls = artistList.get(position).url;
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
            url.setText(item.url);
            urls = item.url;
            txtNombre.setText(item.name);
            listenme.setText(item.streamable);
            views.setText(item.playcount);
        }

    }

    public void swap(List<PojoArtists> newList){
        artistList.clear();
        artistList.addAll(newList);
        notifyDataSetChanged();
    }

    public void filter(List<PojoArtists> artistsfilter){
        this.artistList = artistsfilter;
        notifyDataSetChanged();
    }

}
