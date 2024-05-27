package com.example.recyclapp.modules.profile;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclapp.common.Utils;
import com.example.recyclapp.databinding.ActivityProfileBinding;
import com.example.recyclapp.modules.menus.Home;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.etCode.setText("191999");
        binding.etCode.setEnabled(false);
        binding.etEmail.setText("leospinap@ufpso.edu.co");
        binding.etEmail.setEnabled(false);
        binding.etName.setText("Luis Ospina");
        binding.etName.setEnabled(false);
        binding.etTypeUser.setText("Super administrador");
        binding.etTypeUser.setEnabled(false);
        binding.etIdentification.setText("1066864914");
        binding.etIdentification.setEnabled(false);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Utils.IntentWFinish(ProfileActivity.this, Home.class);
    }
}