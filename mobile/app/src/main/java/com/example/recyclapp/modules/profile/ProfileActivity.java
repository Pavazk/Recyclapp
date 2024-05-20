package com.example.recyclapp.modules.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.recyclapp.R;
import com.example.recyclapp.common.Utils;
import com.example.recyclapp.databinding.ActivityProfileBinding;
import com.example.recyclapp.modules.bins.BinAddActivity;
import com.example.recyclapp.modules.menus.Home;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.etCode.setText("191999");
        binding.etEmail.setText("leospinap@ufpso.edu.co");
        binding.etName.setText("Luis Ospina");
        binding.etTypeUser.setText("Super administrador");
        binding.etIdentification.setText("1066864914");
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Utils.Intent(ProfileActivity.this, Home.class);
    }
}