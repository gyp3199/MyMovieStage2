package com.example.lenovo.mymoviestage2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    Context context;
    ArrayList<Trailer>trailerArrayList;
    public TrailerAdapter(Main2Activity main2Activity, ArrayList<Trailer> trailerArrayList) {
        context=main2Activity;
        this.trailerArrayList=trailerArrayList;

    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.alltrailer,viewGroup,false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int i) {
        Picasso.with(context).load(trailerArrayList.get(i).getId()).into(trailerViewHolder.imageView);
        trailerViewHolder.textView.setText(trailerArrayList.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return trailerArrayList.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imgtrailer);
            textView=itemView.findViewById(R.id.text_trailer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int trailpos=getAdapterPosition();
                    String trUrl="https://www.youtube.com/watch?v=";
                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(trUrl+trailerArrayList.get(trailpos).getKey()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
