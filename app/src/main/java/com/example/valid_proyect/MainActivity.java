package com.example.valid_proyect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.valid_proyect.adapter.ArtistAdapter;
import com.example.valid_proyect.database.ArtistsSql;
import com.example.valid_proyect.fragments.Artist;
import com.example.valid_proyect.fragments.Tracks;
import com.example.valid_proyect.models.PojoArtists;
import com.example.valid_proyect.models.PojoImages;
import com.example.valid_proyect.models.PojoTracks;
import com.example.valid_proyect.utils.Contants;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ViewPager viewPager;
    private String text1,text2;
    private Adapter adapter;
    private EditText search;
    String searchelement;
    RecyclerView recyclerView;
    List<PojoArtists> artistList;
    ArtistAdapter artistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabs = findViewById(R.id.tabselection);
        viewPager = findViewById(R.id.containerpage);
        SetUpViewPager(viewPager,tabs);
        search = findViewById(R.id.search);
        searchelement = search.getText().toString();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //filter(s.toString());
            }
        });
    }

    /*private void filter(String element) {
        String artistl;
        ArtistsSql artistsSql = new ArtistsSql(this);
        Cursor cursor = artistsSql.read();

        List<PojoArtists> pojoArtists= new ArrayList<>();

        while(cursor.moveToNext()){
            PojoArtists artistsTemp = new PojoArtists();
            artistsTemp.name = cursor.getString(Contants.topArtists_name_inx);
            artistl = artistsTemp.name;
            if (artistl.toLowerCase().contains(element.toLowerCase())){
                pojoArtists.add(artistsTemp);
            }
        }
        artistAdapter.filter(pojoArtists);
    }*/

    private void SetUpViewPager(ViewPager viewPager, TabLayout tabs) {
        adapter = new Adapter(getSupportFragmentManager());
        tabs.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
    }

    public class Adapter extends FragmentPagerAdapter {

        public Adapter(FragmentManager fm) { super(fm);}

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    Artist artist = new Artist();
                    return artist;
                case 1:
                    Tracks tracks = new Tracks();
                    return tracks;
            }
            return null;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    text1 = getString(R.string.artist);
                    return text1;
                case 1:
                    text2 = getString(R.string.tracks);
                    return text2;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }}
