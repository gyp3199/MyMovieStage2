package com.example.lenovo.mymoviestage2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {
    Context context;
    List<FavouriteEntity> arrayList;
    public FavAdapter(MainActivity mainActivity, List<FavouriteEntity> favArrayList) {

        context=mainActivity;
        this.arrayList=favArrayList;

    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.allmain,viewGroup,false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder favViewHolder, int i) {
        Picasso.with(context).load(arrayList.get(i).getPoster()).into(favViewHolder.imageView);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.mainimg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    Intent intent=new Intent(context,Main2Activity.class);
                    intent.putExtra("mid",arrayList.get(pos).getId());
                    intent.putExtra("mtitle",arrayList.get(pos).getTitle());
                    intent.putExtra("mvote_avg",arrayList.get(pos).getVote_avg());
                    intent.putExtra("mposter",arrayList.get(pos).getPoster());
                    intent.putExtra("moverview",arrayList.get(pos).getOverview());
                    intent.putExtra("mrelease",arrayList.get(pos).getRelease());
                    context.startActivity(intent);
                }
            });

        }
    }
}
