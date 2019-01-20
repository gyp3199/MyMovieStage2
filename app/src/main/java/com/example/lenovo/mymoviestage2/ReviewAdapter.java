package com.example.lenovo.mymoviestage2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    Context context;
    ArrayList<Reviews> reviewsArrayList;
    public ReviewAdapter(Main2Activity main2Activity, ArrayList<Reviews> reviewsArrayList) {
        context=main2Activity;
        this.reviewsArrayList=reviewsArrayList;

    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.allreview,viewGroup,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        if (reviewsArrayList.size()==0){
            reviewViewHolder.textViewauhtor.setText("no Reviews");
        }else {
            reviewViewHolder.textViewauhtor.setText(reviewsArrayList.get(i).getAuthor());


            reviewViewHolder.textViewcontent.setText(reviewsArrayList.get(i).getContent());
        }

    }

    @Override
    public int getItemCount() {
        return reviewsArrayList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView textViewauhtor,textViewcontent;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewauhtor=itemView.findViewById(R.id.main2author);
            textViewcontent=itemView.findViewById(R.id.main2content);
        }
    }
}
