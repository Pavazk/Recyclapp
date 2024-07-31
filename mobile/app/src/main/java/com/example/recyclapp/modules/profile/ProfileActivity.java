package com.example.recyclapp.modules.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclapp.common.Utils;
import com.example.recyclapp.common.interfaces.APIService;
import com.example.recyclapp.databinding.ActivityProfileBinding;
import com.example.recyclapp.modules.init.InitView;
import com.example.recyclapp.modules.main.data.User;
import com.example.recyclapp.modules.menus.Home;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.etCode.setEnabled(false);
        binding.etEmail.setEnabled(false);
        binding.etName.setEnabled(false);
        binding.etTypeUser.setEnabled(false);
        binding.etIdentification.setEnabled(false);
        if(InitView.isOffline){
            binding.etCode.setText("000000");
            binding.etEmail.setText("email@default.com");
            binding.etName.setText("Name Default");
            binding.etTypeUser.setText("Unkown");
            binding.etIdentification.setText("1234567980");
            return;
        }
        try{
            APIService service = Utils.getRetrofit(this).create(APIService.class);
            service.getUserByCode(Utils.restore(this, Utils.KEY_CODE)).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        User user = response.body();
                        binding.etCode.setText(user.getCode());
                        binding.etEmail.setText(user.getEmail());
                        binding.etName.setText(user.getName());
                        binding.etTypeUser.setText(user.getUserType().getName());
                        binding.etIdentification.setText(user.getIdentification());
                        return;
                    }
                    onBackPressed();
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    onBackPressed();
                    Toast.makeText(ProfileActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Throwable t){
            t.printStackTrace();
            onBackPressed();
            Toast.makeText(ProfileActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Utils.IntentWFinish(ProfileActivity.this, Home.class);
    }
}