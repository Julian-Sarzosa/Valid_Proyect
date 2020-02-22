package com.example.valid_proyect.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valid_proyect.R;
import com.example.valid_proyect.pojo.NewPojoTopArtists;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>{

    private List<NewPojoTopArtists.topartists.artist> artistList;
    Context context;
    IAdapterRecylcer click;

    public ArtistAdapter(Context context, List<NewPojoTopArtists.topartists.artist> artistList, IAdapterRecylcer click  ) {
        this.artistList = artistList;
        this.context = context;
        this.click = click;
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
                click.clickItem(artistList.get(position));
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

        ImageView star,star2;
        TextView txtNombre, listenme, views;
        String name, lisent, view;
        NewPojoTopArtists.topartists.artist item;
        View layout;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView;
            star = itemView.findViewById(R.id.star);
            txtNombre = itemView.findViewById(R.id.txtNameArtist);
            listenme = itemView.findViewById(R.id.txtListen);
            views = itemView.findViewById(R.id.txtViews);
        }

        public void setData(NewPojoTopArtists.topartists.artist item) {
            this.item = item;
            Picasso.with(context).load(item.image.get(0).text).into(star);
            //star2 = star.getDrawable();
            txtNombre.setText(item.name);
            name = txtNombre.getText().toString();
            listenme.setText(item.streamable);
            lisent = listenme.getText().toString();
            views.setText(item.playcount);
            view = views.getText().toString();
            if (!name.isEmpty() && !lisent.isEmpty() && !view.isEmpty()) {
                ContentValues sv = new ContentValues();
                sv.put("name", name);
                sv.put("lisent", name);
                sv.put("view", name);
            }
        }

    }

    public void swap(List<NewPojoTopArtists.topartists.artist> newList){
        artistList.clear();
        artistList.addAll(newList);
        notifyDataSetChanged();
    }

    public interface IAdapterRecylcer{
        void clickItem(NewPojoTopArtists.topartists.artist item);
    }
}