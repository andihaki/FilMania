package dev.andi.filmania.Controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dev.andi.filmania.DetailActivity;
import dev.andi.filmania.R;
import dev.andi.filmania.Controller.GsonObject;

/**
 * Created by macos on 11/27/16.
 */

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.ListHolder> {
    public Context context;
    public List<GsonObject.All.ListMovie> allMovie;

    public RecyclerViewAdapter(Context context,
                               List<GsonObject.All.ListMovie> allMovie){
        this.context = context;
        this.allMovie = allMovie;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, null);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        final String title = allMovie.get(position).getTitle();
        final String poster = "https://image.tmdb.org/t/p/w185"
                + allMovie.get(position).getPoster();
        //final String description = allMovie.get(position).getDescription();
        final String vote = allMovie.get(position).getVote();
        //final String backdrop = "https://image.tmdb.org/t/p/w600" + allMovie.get(position).getBackdrop();
        //final String release_date = allMovie.get(position).getRelease_date().substring(0,4);
        final String id = allMovie.get(position).getId_movie();

        if (title.length() > 22) {
            holder.txtTitle.setText(title.substring(0,19) + "...");
        } else {
            holder.txtTitle.setText(title);
        }

        holder.txtVote.setText(vote);

        Glide
                .with(context)
                .load(
                        poster)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.imgPoster);

        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //casting putExtra
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("title", title);
                //intent.putExtra("description", description);
                intent.putExtra("poster", poster);
                intent.putExtra("vote", vote);
                //intent.putExtra("backdrop", backdrop);
                //intent.putExtra("release_date", release_date);
                intent.putExtra("id", id);

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allMovie.size();
    }

    class ListHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtVote;
        ImageView imgPoster;
        CardView cardItem;

        public ListHolder(View itemView){
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtVote = (TextView) itemView.findViewById(R.id.txt_votes);
            cardItem = (CardView) itemView.findViewById(R.id.movie_item);
            imgPoster = (ImageView) itemView.findViewById(R.id.img_movie_item);
        }
    }
}
