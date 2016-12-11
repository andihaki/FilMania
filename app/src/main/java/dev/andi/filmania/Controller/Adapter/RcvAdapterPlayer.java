package dev.andi.filmania.Controller.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import dev.andi.filmania.R;
import dev.andi.filmania.Controller.GsonObject;

/**
 * Created by macos on 12/5/16.
 */

public class RcvAdapterPlayer extends
        RecyclerView.Adapter<RcvAdapterPlayer.PlayerHolder>{
    public Context context;
    public List<GsonObject.Player.PlayerMovie> playerMovie;

    public RcvAdapterPlayer(Context context,
                            List<GsonObject.Player.PlayerMovie> playerMovie){
        this.context = context;
        this.playerMovie = playerMovie;
    }

    @Override
    public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_detail_item, null);
        return new PlayerHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlayerHolder holder, int position) {
        final String player = playerMovie.get(position).getName();
        final String as = playerMovie.get(position).getAs();
        final String imgPlayer = "https://image.tmdb.org/t/p/w185"
                + playerMovie.get(position).getProfile();
        Log.d("Player", player + " ");
        holder.txtPlayer.setText(player);
        holder.txtAs.setText(as);

        Glide
                .with(context)
                .load(imgPlayer)
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(holder.imgPlayer) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.imgPlayer.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    @Override
    public int getItemCount() {
        int lenP;
        for (lenP = 0; lenP < playerMovie.size(); lenP++){
            if (lenP==5) break;
        }
        /*
        if (lenP.equals(0)){
            lenP = 0;
        } else if (lenP.equals(1)){
            lenP = 1;
        } else if (lenP.equals(2)){
            lenP = 2;
        } else if (lenP.equals(3)){
            lenP = 3;
        } else if (lenP.equals(4)){
            lenP = 4;
        } else {
            lenP = 5;
        }*/
        return lenP;
    }

    class PlayerHolder extends RecyclerView.ViewHolder{
        TextView txtPlayer, txtAs;
        ImageView imgPlayer;

        public PlayerHolder(View itemView){
            super(itemView);
            txtPlayer = (TextView) itemView.findViewById(R.id.txt_player);
            txtAs = (TextView) itemView.findViewById(R.id.txt_as);
            imgPlayer = (ImageView) itemView.findViewById(R.id.img_player);
        }
    }
}
