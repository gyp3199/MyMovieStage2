package com.example.lenovo.mymoviestage2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyStateView recyclerView;
    RequestQueue requestQueue;
    String imageUrl="https://image.tmdb.org/t/p/w500";
    ArrayList<MyMovie>movieArrayList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<FavouriteEntity> favArrayList;
    FavViewModel favViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycle);
        favViewModel=ViewModelProviders.of(this).get(FavViewModel.class);
        favArrayList=new ArrayList<>();
        requestQueue=Volley.newRequestQueue(this);
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("NO NETWORK CONNECTION");
            builder.setTitle("Warning");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });
            builder.setNegativeButton("favourites", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fav();
                }
            });
            builder.show();

        }

        else{

            sharedPreferences=getPreferences(MODE_PRIVATE);
            if (sharedPreferences.getString("key",null)!=null){
                if (sharedPreferences.getString("key",null).equalsIgnoreCase("popular")){
                    mypopularmovies();
                }else if (sharedPreferences.getString("key",null).equalsIgnoreCase("top_rated")){
                    mytopratedmovies();
                }else if (sharedPreferences.getString("key",null).equalsIgnoreCase("favourite")){
                   fav();
                }
                else{
                    mypopularmovies();
                }
            }else{
                mypopularmovies();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mytoprated:
                editor=sharedPreferences.edit();
                editor.putString("key","top_rated");
                editor.apply();
                mytopratedmovies();
                break;
            case R.id.mypopular:
                editor=sharedPreferences.edit();
                editor.putString("key","popular");
                editor.apply();
                mypopularmovies();
                break;
            case R.id.myfav:

                editor=sharedPreferences.edit();
                editor.putString("key","favourite");
                editor.apply();
                fav();


                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void fav(){
       favViewModel.getFavmovies().observe(this, new Observer<List<FavouriteEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteEntity> favouriteEntities) {

                FavAdapter favAdapter=new FavAdapter(MainActivity.this,favouriteEntities);
                if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                }else{
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,4));
                }
                recyclerView.setAdapter(favAdapter);


            }
        });

    }

    public void mytopratedmovies(){

        String topUrl="https://api.themoviedb.org/3/movie/top_rated?api_key=212231b4217b91496d026aea43b242b0";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, topUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    movieArrayList=new ArrayList<>();
                    JSONObject mainjsonObject=new JSONObject(response);
                    JSONArray results=mainjsonObject.getJSONArray("results");
                    for (int i=0;i<results.length();i++){

                        JSONObject object=results.getJSONObject(i);
                        String id=object.getString("id");
                        String title=object.getString("title");
                        String vote_avg=object.getString("vote_average");
                        String poster=imageUrl+object.getString("poster_path");
                        String overview=object.getString("overview");
                        String release=object.getString("release_date");

                        MyMovie myMovie=new MyMovie(id,title,vote_avg,poster,overview,release);
                        movieArrayList.add(myMovie);
                    }
                    MovieAdapter movieAdapter=new MovieAdapter(MainActivity.this,movieArrayList);
                    if (getApplicationContext().getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    }
                    else {
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,4));
                    }
                    recyclerView.setAdapter(movieAdapter);

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
    public void mypopularmovies(){

        String popularUrl="https://api.themoviedb.org/3/movie/popular?api_key=212231b4217b91496d026aea43b242b0";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, popularUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    movieArrayList=new ArrayList<>();
                    JSONObject mainjsonObject=new JSONObject(response);
                    JSONArray results=mainjsonObject.getJSONArray("results");
                    for (int i=0;i<results.length();i++){
                        JSONObject object=results.getJSONObject(i);
                        String id=object.getString("id");
                        String title=object.getString("title");
                        String vote_avg=object.getString("vote_average");
                        String poster=imageUrl+object.getString("poster_path");
                        String overview=object.getString("overview");
                        String release=object.getString("release_date");

                        MyMovie myMovie=new MyMovie(id,title,vote_avg,poster,overview,release);
                        movieArrayList.add(myMovie);

                    }
                    MovieAdapter movieAdapter=new MovieAdapter(MainActivity.this,movieArrayList);
                    if (getApplicationContext().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    }
                    else{
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,4));
                    }
                    recyclerView.setAdapter(movieAdapter);

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
