package com.example.spacex.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SpaceData.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract Dao SpaceDao();

    private static AppDatabase database;
    private static String DATABASE_NAME = "SpaceX";

    public synchronized static  AppDatabase getInstance(Context context) {
        if(database == null) {
            synchronized (AppDatabase.class) {
                if (database == null) {
                    database = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return database;
    }
    static Callback callback=new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsynTask(database);
        }
    };
    static class PopulateAsynTask extends AsyncTask<Void,Void,Void>
    {
        private Dao actorDao;
        PopulateAsynTask(AppDatabase actorDatabase)
        {
            actorDao=actorDatabase.SpaceDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            actorDao.deleteAll();
            return null;
        }
    }
}
