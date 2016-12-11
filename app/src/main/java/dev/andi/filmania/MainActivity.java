package dev.andi.filmania;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dev.andi.filmania.Controller.Adapter.RecyclerViewAdapter;
import dev.andi.filmania.Controller.CloudConnecting;
import dev.andi.filmania.Controller.CloudResult;
import dev.andi.filmania.Controller.CloudURL;
import dev.andi.filmania.Controller.GsonObject;

public class MainActivity extends AppCompatActivity {
    CloudConnecting cloudConnecting;
    CloudURL url;
    GsonObject.All listMovie;
    RecyclerView listMovieItem;
    private String mode = "now_playing";
    RecyclerViewAdapter recyclerViewAdapter;
    private Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //menghilangkan shadow di actionbar
        getSupportActionBar().setElevation(0);

        //movie
        listMovieItem = (RecyclerView) findViewById(R.id.rcvMovie);
        //list film bagi 2
        GridLayoutManager linearLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        listMovieItem.setLayoutManager(linearLayoutManager);
        getMovie(mode);


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.itMovie:
                                setTitle("FilMania");
                                mode ="now_playing";
                                openSearch(false);
                                menu.findItem(R.id.action_search).collapseActionView();
                                getMovie(mode);
                                break;
                            case R.id.itFavorite:
                                mode ="top_rated";
                                setTitle("Film Popular");
                                openSearch(false);
                                menu.findItem(R.id.action_search).collapseActionView();
                                getMovie(mode);
                                break;
                            case R.id.itSoon:
                                mode ="upcoming";
                                setTitle("Segera");
                                openSearch(false);
                                menu.findItem(R.id.action_search).collapseActionView();
                                getMovie(mode);
                                break;
                            case R.id.itSearch:
                                setTitle("Cari film");
                                //startSearch();
                                openSearch(true);
                                menu.findItem(R.id.action_search).expandActionView();
                                break;
                            case R.id.itAbout:
                                setTitle("Tentang Film Mania");
                                openSearch(false);
                                menu.findItem(R.id.action_search).collapseActionView();
                                about();
                                break;
                        }
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //add menu search
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.search, menu);
        MenuItem mnSearch = menu.findItem(R.id.action_search);
        mnSearch.setVisible(false);
        final SearchView barSearch = (SearchView) MenuItemCompat.getActionView(mnSearch);
        barSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mode = mode  + "&query=" + query;
                listMovieItem.setVisibility(View.GONE);
                getMovie(mode);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    public void openSearch(Boolean bol){
        listMovieItem.setVisibility(View.GONE);
        menu.findItem(R.id.action_search).setVisible(bol);
    }

    public void startMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void about(){
        setContentView(R.layout.about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                //onBackPressed();
                startMain();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getMovie(final String mode){

        String location = null;
        String title = getTitle().toString();
        if (title.equals("Cari film")){
            location = url.searchMovie(mode);
        } else {
            location = url.getMovie(mode);
        }
        listMovieItem.setVisibility(View.VISIBLE);
        cloudConnecting = new CloudConnecting();
        cloudConnecting.getData(getApplicationContext(),
                location, new CloudResult() {
                    @Override
                    public void resultData(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        listMovie = gson.fromJson(response, GsonObject.All.class);
                        recyclerViewAdapter = new RecyclerViewAdapter(
                                MainActivity.this, listMovie.getResults());
                        listMovieItem.setAdapter(recyclerViewAdapter);
                    }

                    @Override
                    public void errorResultData(String errorResponse) {
                        Log.d("Error", errorResponse);
                    }
                });
    }
}