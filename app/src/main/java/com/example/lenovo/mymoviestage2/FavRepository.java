package com.example.lenovo.mymoviestage2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FavRepository {
     public FavDao favDao;
     public LiveData<List<FavouriteEntity>> getFavmovie;
     public FavRepository(Application application){

         FavDataBase favDataBase=FavDataBase.getDatabase(application);
         favDao=favDataBase.favDao();
         getFavmovie=favDao.getfavmovies();
     }

    public LiveData<List<FavouriteEntity>> getFavmovies()
    {
        return getFavmovie;
    }
    public FavouriteEntity checkforMovie(String id)
    {
        return favDao.searchmovie(id);
    }

    public void insert(FavouriteEntity favouriteEntity){
          new insertAsync(favDao).execute(favouriteEntity);

    }
    private static class insertAsync extends AsyncTask<FavouriteEntity,Void,Void>{
        private FavDao favDaoasync;
        public insertAsync(FavDao favDao){

            this.favDaoasync=favDao;

        }

        @Override
        protected Void doInBackground(FavouriteEntity... favouriteEntities) {
            favDaoasync.insert(favouriteEntities[0]);

            return null;
        }
    }

    public void delete(FavouriteEntity favouriteEntity){
         new deleteAsync(favDao).execute(favouriteEntity);
    }
    private static class deleteAsync extends AsyncTask<FavouriteEntity,Void,Void>{
         private FavDao nfavdaoasync;
         public  deleteAsync(FavDao favDao){
             this.nfavdaoasync=favDao;
         }
        @Override
        protected Void doInBackground(FavouriteEntity... favouriteEntities) {
             nfavdaoasync.delete(favouriteEntities[0]);
            return null;
        }
    }


}
