package com.example.dogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.dogapp.model.DogBreed;
import com.example.dogapp.viewmodel.DogsApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private DogsApiService apiService;
    private RecyclerView recyclerView;
    private DogsAdapter dogsAdapter;
    private List<DogBreed> dogList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = new DogsApiService();
        recyclerView = findViewById(R.id.rv_dogs);
        dogList = new ArrayList<>();
        apiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DogBreed> dogBreeds) {
                        Log.d("DEBUG","Success");
                        for(DogBreed dog: dogBreeds){
                            Log.d("DEBUG"," " + dog.getName());
                            dogList.clear(); // Xóa dữ liệu cũ
                            dogList.addAll(dogBreeds); // Thêm dữ liệu mới từ API vào danh sách
                            dogsAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG","Fail" + e.getMessage());
                    }
                });
        // Khởi tạo và gán Adapter cho RecyclerView
        dogsAdapter = new DogsAdapter(this,dogList );
        recyclerView.setAdapter(dogsAdapter);

        // Thiết lập LayoutManager cho RecyclerView (LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager, vv...)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}