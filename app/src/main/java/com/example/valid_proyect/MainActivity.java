package com.example.valid_proyect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.valid_proyect.adapter.ArtistAdapter;
import com.example.valid_proyect.adapter.TrackAdapter;
import com.example.valid_proyect.fragments.Artist;
import com.example.valid_proyect.fragments.Tracks;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ViewPager viewPager;
    private String text1,text2;
    private Adapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabs = findViewById(R.id.tabselection);
        viewPager = findViewById(R.id.containerpage);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        SetUpViewPager(viewPager,tabs);
    }

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
    }
}
