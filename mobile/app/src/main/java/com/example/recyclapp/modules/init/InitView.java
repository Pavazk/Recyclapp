package com.example.recyclapp.modules.init;

import static com.example.recyclapp.common.Utils.permissionManager;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclapp.common.Utils;
import com.example.recyclapp.databinding.ActivityInitBinding;
import com.example.recyclapp.modules.main.view.MainView;

import java.util.Timer;
import java.util.TimerTask;

public class InitView extends AppCompatActivity {

    public static boolean isOffline = false;

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
                    Utils.IntentWFinish(InitView.this, MainView.class);
                } else {
                    changeView(binding);
                }
            }
        }, 2500);
    }

    private void changeView(ActivityInitBinding binding) {
        runOnUiThread(() -> {
            binding.splash.setVisibility(View.GONE);
            binding.tvWifi.setVisibility(View.VISIBLE);
            binding.ivWifi.setVisibility(View.VISIBLE);
            binding.ivWifi.setOnClickListener(view -> start(binding));
        });
    }

    private boolean isInternetAvailable() {
        if (isOffline) {
            return true;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }
}