package com.example.spacex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacex.data.AppDatabase;
import com.example.spacex.data.Dao;
import com.example.spacex.data.SpaceData;
import com.example.spacex.network.Retrofit;
import com.example.spacex.network.SpaceXapi;
import com.example.spacex.recycler.Adapter;
import com.example.spacex.recycler.Repository;
import com.example.spacex.recycler.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SpaceXapi apiInterface;
    RecyclerView recyclerView;
    Repository repository;
    ViewModel viewModel;
    ImageView refresh,delete;
    TextView txtError;
    AppDatabase db;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        refresh = findViewById(R.id.refresh);
        delete = findViewById(R.id.delete);
        txtError = findViewById(R.id.refreshText);
        pb = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        db = AppDatabase.getInstance(getApplicationContext());
        repository = new Repository(getApplication());

        network();

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getAll().observe(this, new Observer<List<SpaceData>>() {
            @Override
            public void onChanged(List<SpaceData> data) {
                if(data.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    txtError.setVisibility(View.VISIBLE);
                }
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new Adapter(data));
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                txtError.setVisibility(View.INVISIBLE);
                network();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtError.setVisibility(View.VISIBLE);
                pb.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.GONE);
                new DeleteAsynTask(db).execute();

            }
        });


    }

    private void network() {
        Retrofit retrofit = new Retrofit();
        Call<List<SpaceData>> call = retrofit.api.crewData();
        call.enqueue(new Callback<List<SpaceData>>() {
            @Override
            public void onResponse(Call<List<SpaceData>> call, Response<List<SpaceData>> response) {
                if(response.body()!=null) {
                    pb.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    txtError.setVisibility(View.INVISIBLE);
                    repository.insert(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<SpaceData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error Data not load...", Toast.LENGTH_SHORT).show();
            }
        });

    }


    static class DeleteAsynTask extends AsyncTask<Void,Void,Void> {
            private Dao dao;
            DeleteAsynTask(AppDatabase db)
            {
                dao=db.SpaceDao();
            }
            @Override
            protected Void doInBackground(Void... voids) {
                dao.deleteAll();
                return null;
            }
        }


}
