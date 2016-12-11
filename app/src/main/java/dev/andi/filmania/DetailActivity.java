package dev.andi.filmania;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import dev.andi.filmania.Controller.Adapter.RcvAdapterPlayer;
import dev.andi.filmania.Controller.CloudConnecting;
import dev.andi.filmania.Controller.CloudResult;
import dev.andi.filmania.Controller.CloudURL;
import dev.andi.filmania.Controller.GsonObject;

/**
 * Created by macos on 11/29/16.
 */

public class DetailActivity extends AppCompatActivity {

    private TextView txtTitle, txtDescription, txtVote, txtReleaseDate, txtGenre, txtDuration,
                        txtPlayer, txtCompany, txtAs;
    private ImageView imgPoster, imgBackdrop, imgPlayer;

    CloudConnecting cloudConnecting;
    CloudURL url;
    GsonObject.Detail detailMovie;
    GsonObject.Player playerMovie;
    GsonObject.Video videoMovie;
    RcvAdapterPlayer rcvAdapterPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        //Casting
        txtTitle = (TextView) findViewById(R.id.txt_titles);
        txtDescription = (TextView) findViewById(R.id.txt_descriptions);
        txtVote = (TextView) findViewById(R.id.txt_votes);
        txtReleaseDate = (TextView) findViewById(R.id.txt_release_dates);
        imgPoster = (ImageView) findViewById(R.id.img_posters);
        imgBackdrop = (ImageView) findViewById(R.id.img_backdrops);
        txtGenre = (TextView) findViewById(R.id.txt_genre);
        txtDuration = (TextView) findViewById(R.id.txt_duration);
        txtPlayer = (TextView) findViewById(R.id.txt_player);
        txtCompany = (TextView) findViewById(R.id.txt_company);
        txtAs = (TextView) findViewById(R.id.txt_as);
        imgPlayer = (ImageView) findViewById(R.id.img_player);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getIntent().getStringExtra("title"));

        getDetail(getIntent().getStringExtra("id"));
        getPlayer(getIntent().getStringExtra("id"));
        getVideo(getIntent().getStringExtra("id"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share, menu);
        MenuItem SharedContent = menu.findItem(R.id.itShare);
        SharedContent.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "[Film Mania] \nJudul film: " + detailMovie.getTitle() + "\n"
                        + "Rating: " + detailMovie.getVote() + "\n"
                        + "Tayang: " + detailMovie.getRelease() + "\n"
                        + "Cuplikan: " + "https://www.youtube.com/watch?v=" + videoMovie.video.get(0).getKey();

                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Film Mania");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share film ini"));
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void getDetail(final String id){
        cloudConnecting = new CloudConnecting();
        cloudConnecting.getData(getApplicationContext(),
                url.getDetail(id), new CloudResult() {
                    @Override
                    public void resultData(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        detailMovie = gson.fromJson(response, GsonObject.Detail.class);
                        txtTitle.setText(detailMovie.getTitle());
                        txtDescription.setText(detailMovie.getDescription());
                        txtReleaseDate.setText(detailMovie.getRelease().substring(0,4));
                        txtVote.setText(detailMovie.getVote());

                        Glide
                                .with(getApplicationContext())
                                .load("https://image.tmdb.org/t/p/w185" + detailMovie.getPoster())
                                .centerCrop()
                                .placeholder(R.mipmap.ic_launcher)
                                .crossFade()
                                .into(imgPoster);

                        Glide
                                .with(getApplicationContext())
                                .load("https://image.tmdb.org/t/p/w600" + detailMovie.getBackdrop())
                                .centerCrop()
                                .placeholder(R.mipmap.ic_launcher)
                                .crossFade()
                                .into(imgBackdrop);

                        txtDuration.setText(detailMovie.getDuration());
                        ArrayList<String> genreList = new ArrayList<>();
                        for (int i = 0; i < detailMovie.genre.size(); i ++){
                            genreList.add(detailMovie.genre.get(i).getGenre());
                        }
                        txtGenre.setText(genreList.toString().substring(1, genreList.toString().length()-1));
                        ArrayList<String> companyList = new ArrayList<>();
                        for (int i = 0; i <detailMovie.companies.size(); i++){
                            companyList.add(detailMovie.companies.get(i).getName());
                        }
                        txtCompany.setText(companyList.toString().substring(1, companyList.toString().length()-1));

                        imgBackdrop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String key = "";
                                if (videoMovie.video.size() == 0) {
                                    key = videoMovie.video.get(0).getKey();
                                } else {
                                    key = videoMovie.video.get(1).getKey();
                                }
                                String url = "https://www.youtube.com/watch?v=" + key;
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
                    }

                    @Override
                    public void errorResultData(String errorResponse) {
                        Log.d("Error", errorResponse);
                    }
                });
    }

    public void getPlayer(final String id){
        cloudConnecting = new CloudConnecting();
        cloudConnecting.getData(getApplicationContext(), url.getPlayer(id),
                new CloudResult() {
                    @Override
                    public void resultData(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        playerMovie = gson.fromJson(response, GsonObject.Player.class);

                        //recycler player
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcvPlayer);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(
                                DetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        rcvAdapterPlayer = new RcvAdapterPlayer(
                                DetailActivity.this, playerMovie.getPlayer());
                        recyclerView.setAdapter(rcvAdapterPlayer);

                    }

                    @Override
                    public void errorResultData(String errorResponse) {

                    }
                });
    }

    public void getVideo(final String id) {
        cloudConnecting = new CloudConnecting();
        cloudConnecting.getData(getApplicationContext(), url.getVideo(id),
                new CloudResult() {
                    @Override
                    public void resultData(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        videoMovie = gson.fromJson(response, GsonObject.Video.class);
                    }

                    @Override
                    public void errorResultData(String errorResponse) {

                    }
                });
    }
}
