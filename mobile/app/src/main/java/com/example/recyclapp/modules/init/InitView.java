package com.example.recyclapp.modules.init;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.example.recyclapp.common.Utils.permissionManager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
        permissionManager(this);
        start(binding);
        Utils.save(this, Utils.KEY_CODE, "");
        Utils.save(this, Utils.KEY_ROLE, "");
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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (!checkPermissionResult(grantResults)) {
                Toast.makeText(this, "Por favor acepte los permisos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkPermissionResult(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == -1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}