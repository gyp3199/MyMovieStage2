package com.example.lenovo.mymoviestage2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface FavDao {

    @Insert
    void insert(FavouriteEntity favouriteEntity);

    @Query("SELECT * FROM fav_table")
    LiveData<List<FavouriteEntity>>getfavmovies();

    @Delete
    void delete(FavouriteEntity favouriteEntity);

    @Query("SELECT * FROM fav_table WHERE fid == :id")
    FavouriteEntity searchmovie(String id);


}
