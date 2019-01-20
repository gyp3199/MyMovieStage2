package com.example.lenovo.mymoviestage2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavouriteEntity.class},version = 1,exportSchema = false)
public abstract class FavDataBase extends RoomDatabase {

    public abstract FavDao favDao();
    private static volatile FavDataBase INSTANCE;

    static FavDataBase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (FavDao.class){
                if (INSTANCE == null){
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(),
                            FavDataBase.class,"word_database").allowMainThreadQueries().fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
