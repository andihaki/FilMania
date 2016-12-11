package dev.andi.filmania.Controller;

/**
 * Created by macos on 11/26/16.
 */

public class CloudURL {
    final static public String BASE_URL = "http://api.themoviedb.org/3/movie/";
    final static public String API_KEY = "e20a79749b697e2ca678b87f63d1f26c";
    final static public String SEARCH_URL = "http://api.themoviedb.org/3/search/movie";

    public static String getMovie(String mode){
        //https://api.themoviedb.org/3/movie/now_playing?api_key=e20a79749b697e2ca678b87f63d1f26c
        return BASE_URL + mode + "?api_key=" + API_KEY;
    }

    public static String searchMovie(String mode){
        return SEARCH_URL + "?" + mode + "&api_key=" + API_KEY;
    }

    public static String getDetail(String id){
        //https://api.themoviedb.org/3/movie/259316?api_key=e20a79749b697e2ca678b87f63d1f26c
        return BASE_URL + id + "?api_key=" + API_KEY;
    }

    public static String getPlayer(String id){
        //https://api.themoviedb.org/3/movie/259316/credits?api_key=e20a79749b697e2ca678b87f63d1f26c;
        return BASE_URL + id + "/credits?api_key=" + API_KEY;
    }

    public static String getVideo(String id){
        //https://api.themoviedb.org/3/movie/259316/videos?api_key=e20a79749b697e2ca678b87f63d1f26c
        return BASE_URL + id + "/videos?api_key=" + API_KEY;
    }
}
