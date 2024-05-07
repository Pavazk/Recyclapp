package com.example.isana.modules.main.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.isana.databinding.ActivityMainBinding;
import com.example.isana.modules.main.presenter.MainPresenter;

public class MainView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new MainPresenter(this).init(binding);
    }

    @Override
    public void onBackPressed() {
    }
}