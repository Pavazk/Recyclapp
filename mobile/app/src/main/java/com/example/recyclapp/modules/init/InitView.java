package com.example.recyclapp.modules.init;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclapp.databinding.ActivityInitBinding;
import com.example.recyclapp.modules.main.view.MainView;
import com.example.recyclapp.common.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class InitView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInitBinding binding = ActivityInitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        start(binding);
    }

    private void start(ActivityInitBinding binding) {
        binding.splash.setVisibility(View.VISIBLE);
        binding.tvWifi.setVisibility(View.GONE);
        binding.ivWifi.setVisibility(View.GONE);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isInternetAvailable()) {
                    Utils.Intent(InitView.this, MainView.class);
                } else {
                    cambiarVista(binding);
                }
            }
        }, 2500);
    }

    private void cambiarVista(ActivityInitBinding binding) {
        runOnUiThread(() -> {
            binding.splash.setVisibility(View.GONE);
            binding.tvWifi.setVisibility(View.VISIBLE);
            binding.ivWifi.setVisibility(View.VISIBLE);
            binding.ivWifi.setOnClickListener(view -> start(binding));
        });
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