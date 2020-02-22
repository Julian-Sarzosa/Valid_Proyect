package com.example.valid_proyect.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewPojoTopTracks {
    public tracks tracks;

    public class tracks {
        public List<track> track = null;
        @SerializedName("@attr")
        public attr attr;

        public class track {
            @SerializedName("@attr")
            public attr attr;
            public String mbid;
            public String listeners;
            public String duration;
            public String name;
            public String url;

            public streamable streamable;
            public artist artist;
            public List<image> image = null;


            public class artist {
                public String name;
                public String mbid;
                public String url;
            }

            public class image {
                public String size;
                @SerializedName("#text")
                public String text;
            }

            public class streamable {
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
