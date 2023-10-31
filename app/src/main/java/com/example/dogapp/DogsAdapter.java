package com.example.dogapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import android.content.Context;
import android.view.LayoutInflater;

import android.widget.ImageView;

import com.example.dogapp.model.DogBreed;
import java.util.List;


public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogViewHolder> {
    private List<DogBreed> dogList;
    private Context context;

    public DogsAdapter(Context context, List<DogBreed> dogList) {
        this.context = context;
        this.dogList = dogList;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dog_list_item, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        DogBreed dog = dogList.get(position);

        // Hiển thị tên giống chó lên TextView
        holder.dogNameTextView.setText(dog.getName());

        // Sử dụng thư viện Glide để tải và hiển thị hình ảnh từ URL lên ImageView
        Glide.with(context)
                .load(dog.getUrl())
                .into(holder.dogImageView);
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    public class DogViewHolder extends RecyclerView.ViewHolder {
        public ImageView dogImageView;
        public TextView dogNameTextView;

        public DogViewHolder(View itemView) {
            super(itemView);
            dogImageView = itemView.findViewById(R.id.dogImage);
            dogNameTextView = itemView.findViewById(R.id.dogName);
        }
    }
}
