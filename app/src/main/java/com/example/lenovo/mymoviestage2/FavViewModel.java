package com.example.lenovo.mymoviestage2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FavViewModel extends AndroidViewModel {
    public FavRepository favRepository;
    public LiveData<List<FavouriteEntity>> getFavMovie;
    FavouriteEntity favouritedata;

    public FavViewModel(@NonNull Application application) {
        super(application);
         favRepository=new FavRepository(application);
        getFavMovie=favRepository.getFavmovies();
    }

    LiveData<List<FavouriteEntity>> getFavmovies(){return getFavMovie;}

    public FavouriteEntity checkfav(String id){
        favouritedata=favRepository.checkforMovie(id);
        return favouritedata;
    }
    public void insert(FavouriteEntity favouriteEntity){favRepository.insert(favouriteEntity);}
    public void delete(FavouriteEntity favouriteEntity){favRepository.delete(favouriteEntity);}
}
