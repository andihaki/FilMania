package dev.andi.filmania.Controller;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by macos on 11/26/16.
 */

public class GsonObject {
    public class All {
        @SerializedName("results")
        public List<ListMovie> results;

        public List<ListMovie> getResults() {
            return results;
        }

        public class ListMovie {
            @SerializedName("poster_path")
            public String poster;

            @SerializedName("overview")
            public String description;

            @SerializedName("release_date")
            public String release_date;

            @SerializedName("id")
            public String id_movie;

            @SerializedName("title")
            public String title;

            @SerializedName("backdrop_path")
            public String backdrop;

            @SerializedName("vote_average")
            public String vote;

            public String getPoster() {
                return poster;
            }

            public String getDescription() {
                return description;
            }

            public String getRelease_date() {
                return release_date;
            }

            public String getId_movie() {
                return id_movie;
            }

            public String getTitle() {
                return title;
            }

            public String getBackdrop() {
                return backdrop;
            }

            public String getVote() {
                return vote;
            }

        }
    }

    public class Detail {
        @SerializedName("genres")
        public List<Genre> genre;

        public List<Genre> getGenre() {
            return genre;
        }

        public class Genre {
            @SerializedName("name")
            public String genre;

            public String getGenre() {
                return genre;
            }
        }

        @SerializedName("production_companies")
        public List<Company> companies;

        public List<Company> getCompanies() {
            return companies;
        }

        public class Company {
            @SerializedName("name")
            public String name;

            public String getName() {
                return name;
            }
        }

        @SerializedName("original_title")
        public String title;

        @SerializedName("overview")
        public String description;


        @SerializedName("poster_path")
        public String poster;

        @SerializedName("release_date")
        public String release;

        @SerializedName("runtime")
        public String duration;

        @SerializedName("vote_average")
        public String vote;

        @SerializedName("backdrop_path")
        public String backdrop;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getPoster() {
            return poster;
        }

        public String getRelease() {
            return release;
        }

        public String getDuration() {
            return duration;
        }

        public String getVote() {
            return vote;
        }

        public String getBackdrop() {
            return backdrop;
        }
    }

    public class Player {
        @SerializedName("cast")
        public List<PlayerMovie> player;

        public List<PlayerMovie> getPlayer() {
            return player;
        }

        public class PlayerMovie {
            @SerializedName("character")
            public String as;

            @SerializedName("name")
            private String name;

            @SerializedName("profile_path")
            private String profile;

            public String getAs() {
                return as;
            }

            public String getName() {
                return name;
            }

            public String getProfile() {
                return profile;
            }
        }
    }

    public class Video {
        @SerializedName("results")
        public List<VideoMovie> video;

        public List<VideoMovie> getVideo() {
            return video;
        }

        public class VideoMovie{
            @SerializedName("key")
            public String key;

            public String getKey() {
                return key;
            }

            @SerializedName("name")
            public String name;

            public String getName() {
                return name;
            }
        }
    }
}
