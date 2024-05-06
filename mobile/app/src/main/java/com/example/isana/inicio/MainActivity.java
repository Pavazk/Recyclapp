package com.example.isana.inicio;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.isana.databinding.ActivityMainBinding;
import com.example.isana.login.Login;
import com.example.isana.multiusos.Control;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(binding);
    }

    private void init(ActivityMainBinding binding) {
        binding.splash.setVisibility(View.VISIBLE);
        binding.tvWifi.setVisibility(View.GONE);
        binding.ivWifi.setVisibility(View.GONE);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isInternetAvailable()) {
                    Control.Intent(MainActivity.this, Login.class);
                } else {
                    cambiarVista(binding);
                }
            }
        }, 2500);
    }

    private void cambiarVista(ActivityMainBinding binding) {
        binding.splash.setVisibility(View.GONE);
        binding.tvWifi.setVisibility(View.VISIBLE);
        binding.ivWifi.setVisibility(View.VISIBLE);
        binding.ivWifi.setOnClickListener(view -> init(binding));
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
    }
}