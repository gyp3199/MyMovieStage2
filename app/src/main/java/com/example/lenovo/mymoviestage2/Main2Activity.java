package com.example.lenovo.mymoviestage2;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    ImageView imageView;
    TextView id_tv,title_tv, vote_tv, overview_tv, release_tv;
    String nid,ntitle, nvote, nposter, noverview, nrelease;
    RecyclerView recyclerViewreview,recyclerViewtrail;
    RequestQueue requestQueue;
    ArrayList<Reviews>reviewsArrayList;
    ArrayList<Trailer>trailerArrayList;
    MaterialFavoriteButton materialFavoriteButton;
    FavViewModel favViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requestQueue=Volley.newRequestQueue(this);
        materialFavoriteButton=findViewById(R.id.favbutton);
        favViewModel=ViewModelProviders.of(this).get(FavViewModel.class);
        recyclerViewreview=findViewById(R.id.revrec);
        recyclerViewtrail=findViewById(R.id.trailerrec);
        imageView = findViewById(R.id.main2img);
        id_tv=findViewById(R.id.main2id);
        title_tv = findViewById(R.id.main2title);
        vote_tv = findViewById(R.id.main2vote);
        overview_tv = findViewById(R.id.main2overview);
        release_tv = findViewById(R.id.main2release);
        
        nid=getIntent().getStringExtra("mid");
        ntitle = getIntent().getStringExtra("mtitle");
        nvote = getIntent().getStringExtra("mvote_avg");
        nposter = getIntent().getStringExtra("mposter");
        noverview = getIntent().getStringExtra("moverview");
        nrelease = getIntent().getStringExtra("mrelease");
        Picasso.with(this).load(nposter).into(imageView);
        id_tv.setText(nid);
        title_tv.setText(ntitle);
        vote_tv.setText(nvote);
        overview_tv.setText(noverview);
        release_tv.setText(nrelease);
        myreviews();
        mytrailers();
        searchForMovie();
        addFavdata();
    }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }
        public void searchForMovie(){
            FavouriteEntity moviepresent=favViewModel.checkfav(nid);
            if (moviepresent != null){
                materialFavoriteButton.setFavorite(true);
            }
            else {
                materialFavoriteButton.setFavorite(false);
            }
        
        }
        public void addFavdata(){
        
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite){
                        savedata();
                        Toast.makeText(Main2Activity.this, "Movie is added to favourites", Toast.LENGTH_SHORT).show();
                    }else{
                        deletedata();
                        Toast.makeText(Main2Activity.this, "Movie is removed from favourites", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            
        }
        
        public void savedata(){
         FavouriteEntity favourite=new FavouriteEntity();
         favourite.setId(nid);
         favourite.setTitle(ntitle);
         favourite.setVote_avg(nvote);
         favourite.setPoster(nposter);
         favourite.setOverview(noverview);
         favourite.setRelease(nrelease);
         favViewModel.insert(favourite);
        }
        public void deletedata(){
        FavouriteEntity favourite=new FavouriteEntity();
        favourite.setId(nid);
        favourite.setTitle(ntitle);
        favourite.setVote_avg(nvote);
        favourite.setPoster(nposter);
        favourite.setOverview(noverview);
        favourite.setRelease(nrelease);
        favViewModel.delete(favourite);
        }

        public void myreviews(){

        String reviewUrl="https://api.themoviedb.org/3/movie/363088/reviews?api_key=212231b4217b91496d026aea43b242b0";
            StringRequest stringRequest=new StringRequest(Request.Method.GET, reviewUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        reviewsArrayList=new ArrayList<>();
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray results=jsonObject.getJSONArray("results");
                        for (int i=0;i<results.length();i++){
                            JSONObject object=results.getJSONObject(i);
                            String author=object.getString("author");
                            String content=object.getString("content");

                            Reviews reviews=new Reviews(author,content);
                            reviewsArrayList.add(reviews);
                        }

                        ReviewAdapter reviewAdapter=new ReviewAdapter(Main2Activity.this,reviewsArrayList);
                        recyclerViewreview.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
                        recyclerViewreview.setAdapter(reviewAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(stringRequest);

        }

        public void mytrailers(){
            String trailerUrl="https://api.themoviedb.org/3/movie/363088/videos?api_key=212231b4217b91496d026aea43b242b0";
            StringRequest stringRequest=new StringRequest(Request.Method.GET, trailerUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    trailerArrayList=new ArrayList<>();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray results=jsonObject.getJSONArray("results");
                        for (int i=0;i<results.length();i++){
                            JSONObject object=results.getJSONObject(i);
                            String id=object.getString("id");
                            String key=object.getString("key");
                            String name=object.getString("name");
                            String type=object.getString("type");

                            Trailer trailer=new Trailer(id,key,name,type);
                            trailerArrayList.add(trailer);
                        }
                        TrailerAdapter trailerAdapter=new TrailerAdapter(Main2Activity.this,trailerArrayList);
                        recyclerViewtrail.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
                        recyclerViewtrail.setAdapter(trailerAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(stringRequest);

        }

}
