package com.example.valid_proyect.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewPojoTopArtists {
    public topartists topartists;

    public class topartists {
        public List<artist> artist = null;
        @SerializedName("@attr")
        public attr attr;

        public class artist {
            @SerializedName("@attr")
            public attr attr;
            public String mbid;
            public String url;
            public String playcount;
            public String name;
            public String streamable;
            public List<image> image = null;

            public class image {
                public String size;
                @SerializedName("#text")
                public String text;
            }

            public class attr {
                public String rank;
            }
        }

        public class attr {
            public String page;
            public String perPage;
            public String user;
            public String total;
            public String totalPages;
        }
    }

}
