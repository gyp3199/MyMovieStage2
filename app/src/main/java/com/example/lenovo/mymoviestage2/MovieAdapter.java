package com.example.lenovo.mymoviestage2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    Context context;
    ArrayList<MyMovie>movieArrayList;
    public MovieAdapter(MainActivity mainActivity, ArrayList<MyMovie> movieArrayList) {
        context=mainActivity;
        this.movieArrayList=movieArrayList;

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.allmain,viewGroup,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {

        Picasso.with(context).load(movieArrayList.get(i).getPoster()).placeholder(R.mipmap.ic_launcher).into(movieViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.mainimg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapos=getAdapterPosition();
                    Intent intent=new Intent(context,Main2Activity.class);
                    intent.putExtra("mid",movieArrayList.get(adapos).getId());
                    intent.putExtra("mtitle",movieArrayList.get(adapos).getTitle());
                    intent.putExtra("mvote_avg",movieArrayList.get(adapos).getVote_avg());
                    intent.putExtra("mposter",movieArrayList.get(adapos).getPoster());
                    intent.putExtra("moverview",movieArrayList.get(adapos).getOverview());
                    intent.putExtra("mrelease",movieArrayList.get(adapos).getRelease());
                    context.startActivity(intent);
                }
            });
        }
    }
}
