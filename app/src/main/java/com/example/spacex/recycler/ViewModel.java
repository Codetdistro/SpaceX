package com.example.spacex.recycler;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.spacex.data.SpaceData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<SpaceData>> spaceData;
    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        spaceData = repository.getAllData();
    }

    public void insert(List<SpaceData> list)
    {
        repository.insert(list);
    }

    public LiveData<List<SpaceData>> getAll()
    {
        return spaceData;
    }

}
