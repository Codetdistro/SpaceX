package com.example.spacex.recycler;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.spacex.data.AppDatabase;
import com.example.spacex.data.Dao;
import com.example.spacex.data.SpaceData;

import java.util.List;

public class Repository {
    private AppDatabase db;
    private LiveData<List<SpaceData>> memberdata;

    public Repository(Application application)
    {
        db=AppDatabase.getInstance(application);
        memberdata = db.SpaceDao().getAll();
    }

    public void insert(List<SpaceData> data){
        new InsertAsynTask(db).execute(data);
    }

    public LiveData<List<SpaceData>> getAllData()
    {
        return memberdata;
    }

    static class InsertAsynTask extends AsyncTask<List<SpaceData>,Void,Void> {
        private Dao dao;
        InsertAsynTask(AppDatabase db)
        {
            dao=db.SpaceDao();
        }
        @Override
        protected Void doInBackground(List<SpaceData>...lists) {
            if(lists[0] != null) {
                dao.insert(lists[0]);
            }
            return null;
        }
    }
}
