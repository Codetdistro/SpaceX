package com.example.spacex.recycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spacex.R;
import com.example.spacex.data.SpaceData;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<SpaceData> data;

    public Adapter(List<SpaceData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        Log.i("Code","This is "+data.get(position).toString());
        holder.agency.setText(data.get(position).getAgency());
        holder.status.setText(data.get(position).getStatus());
        Glide.with(holder.itemView)
                .load(data.get(position).getImage())
                .into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView userImage;
        public TextView name,agency,wiki,status;
        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.agency = (TextView) itemView.findViewById(R.id.agency);
            this.wiki = (TextView) itemView.findViewById(R.id.wikipedia);
            this.status = (TextView) itemView.findViewById(R.id.status);
            this.userImage = (ImageView) itemView.findViewById(R.id.member_Image);
        }
    }
}
