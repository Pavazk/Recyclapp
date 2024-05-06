package com.example.isana.login;

import static com.example.isana.multiusos.Control.IP;
import static com.example.isana.multiusos.Control.SHA256;
import static com.example.isana.multiusos.Control.getValuePreference;
import static com.example.isana.multiusos.Control.saveValuePreference;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.isana.databinding.ActivityLoginBinding;
import com.example.isana.menus.Home;
import com.example.isana.multiusos.Control;
import com.example.isana.R;
import com.example.isana.interfaces.APIService;
import com.example.isana.multiusos.Person;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Type;
import java.util.Arrays;

import okhttp3.ConnectionSpec;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;

public class Login extends AppCompatActivity {

    public static final String  URL = "http://10.122.244.125:9999/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        showLogin(binding);
        onClick(binding);
        rules(binding);
    }

    private void registerUser(ActivityLoginBinding binding) {
        String name = binding.etName.getText().toString();
        String code = binding.etCode.getText().toString();
        String identification = binding.etIdentification.getText().toString();
        String email = binding.etEmail.getText().toString();
        User user = new User(email, code, name, identification);
        try {
            APIService service = Control.getRetrofit(URL).create(APIService.class);
            Call<User> answerCall = service.registerUser(user);
            answerCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    showLogin(binding);
                    Toast.makeText(Login.this, "RESPONDIÓ", Toast.LENGTH_SHORT).show();
                    Log.e("RETROFIT", "onResponse: " + response);
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    showLogin(binding);
                    Toast.makeText(Login.this, "FALLÓ", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            showLogin(binding);
            Toast.makeText(this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser(ActivityLoginBinding binding) {
        String email = binding.etEmail.getText().toString();
        String password = binding.etOldPassword.getText().toString();
        UserLogin user = new UserLogin(email, password);
        try {
            APIService service = Control.getRetrofit(URL).create(APIService.class);
            Call<User> answerCall = service.loginUser(user);
            answerCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    showLogin(binding);
                    Toast.makeText(Login.this, "RESPONDIÓ", Toast.LENGTH_SHORT).show();
                    Log.e("RETROFIT", "onResponse: " + response);
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    showLogin(binding);
                    Toast.makeText(Login.this, "FALLÓ", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
            showLogin(binding);
        }
    }

    @Override
    public void onBackPressed() {}
    private void rules(ActivityLoginBinding binding) {
        binding.etName.setSingleLine(true);
        binding.etCode.setSingleLine(true);
        binding.etIdentification.setSingleLine(true);
        binding.etEmail.setSingleLine(true);
        binding.etOldPassword.setSingleLine(true);
        binding.etNewPassword.setSingleLine(true);
        binding.etRepeatNewPassword.setSingleLine(true);
    }
    private void onClick(ActivityLoginBinding binding) {
        binding.tvFooter.setOnClickListener(view -> {
            switch (binding.tvTitulo.getText().toString()){
                case "INICIO DE SESIÓN":
                    showRegister(binding);
                    break;
                case "REGISTRO DE USUARIO":
                    showLogin(binding);
            }
        });
        binding.button.setOnClickListener(view -> {
            showLoading(binding);
            switch (binding.tvTitulo.getText().toString()){
                case "INICIO DE SESIÓN":
                    loginUser(binding);
                    break;
                case "REGISTRO DE USUARIO":
                    registerUser(binding);
                    break;
                case "CAMBIAR CONTRASEÑA":
                    //todo: crear metodo para cambiar contraseña
                    break;
            }
        });
    }
    private void showLoading(ActivityLoginBinding binding) {
        binding.cargando.setVisibility(View.VISIBLE);
        binding.ivIcon.setVisibility(View.GONE);
        binding.tvTitulo.setVisibility(View.GONE);
        binding.tvSubTitulo.setVisibility(View.GONE);
        binding.linearLayout.setVisibility(View.GONE);
        binding.tvFooter.setVisibility(View.GONE);
        binding.button.setVisibility(View.GONE);
    }
    private void showLogin(ActivityLoginBinding binding){
        binding.cargando.setVisibility(View.GONE);
        binding.ivIcon.setVisibility(View.VISIBLE);
        binding.tvTitulo.setVisibility(View.VISIBLE);
        binding.tvSubTitulo.setVisibility(View.GONE);

        binding.linearLayout.setVisibility(View.VISIBLE);

        binding.tfName.setVisibility(View.GONE);
        binding.tfCode.setVisibility(View.GONE);
        binding.tfIdentification.setVisibility(View.GONE);

        binding.tfEmail.setVisibility(View.VISIBLE);
        binding.tfOldPassword.setVisibility(View.VISIBLE);

        binding.tfNewPassword.setVisibility(View.GONE);
        binding.tfRepeatNewPassword.setVisibility(View.GONE);

        binding.tvFooter.setVisibility(View.VISIBLE);
        binding.button.setVisibility(View.VISIBLE);

        setTextLogin(binding);
    }
    private void showRegister(ActivityLoginBinding binding){
        binding.cargando.setVisibility(View.GONE);
        binding.ivIcon.setVisibility(View.VISIBLE);
        binding.tvTitulo.setVisibility(View.VISIBLE);
        binding.tvSubTitulo.setVisibility(View.GONE);

        binding.linearLayout.setVisibility(View.VISIBLE);
        binding.tfName.setVisibility(View.VISIBLE);
        binding.tfCode.setVisibility(View.VISIBLE);
        binding.tfIdentification.setVisibility(View.VISIBLE);
        binding.tfEmail.setVisibility(View.VISIBLE);

        binding.tfOldPassword.setVisibility(View.GONE);
        binding.tfNewPassword.setVisibility(View.GONE);
        binding.tfRepeatNewPassword.setVisibility(View.GONE);

        binding.tvFooter.setVisibility(View.VISIBLE);
        binding.button.setVisibility(View.VISIBLE);

        setTextRegister(binding);
    }
    private void showChangePassword(ActivityLoginBinding binding){
        binding.cargando.setVisibility(View.GONE);
        binding.ivIcon.setVisibility(View.VISIBLE);
        binding.tvTitulo.setVisibility(View.VISIBLE);
        binding.tvSubTitulo.setVisibility(View.VISIBLE);

        binding.linearLayout.setVisibility(View.VISIBLE);
        binding.tfName.setVisibility(View.GONE);
        binding.tfCode.setVisibility(View.GONE);
        binding.tfIdentification.setVisibility(View.GONE);
        binding.tfEmail.setVisibility(View.VISIBLE);

        binding.tfOldPassword.setVisibility(View.VISIBLE);
        binding.tfNewPassword.setVisibility(View.VISIBLE);
        binding.tfRepeatNewPassword.setVisibility(View.VISIBLE);

        binding.tvFooter.setVisibility(View.VISIBLE);
        binding.button.setVisibility(View.VISIBLE);

        setTextChangePassword(binding);
    }
    private void setTextLogin(ActivityLoginBinding binding){
        binding.tvTitulo.setText("INICIO DE SESIÓN");
        binding.tfEmail.setHint("Ingrese su email");
        binding.tfOldPassword.setHint("Ingrese su contraseña");
        binding.tvFooter.setText("Si no tiene un usuario haga clic aquí");
        binding.button.setText("Ingresar");
    }
    private void setTextRegister(ActivityLoginBinding binding){
        binding.tvTitulo.setText("REGISTRO DE USUARIO");
        binding.tfName.setHint("Ingrese su nombre");
        binding.tfCode.setHint("Ingrese su código");
        binding.tfIdentification.setHint("Ingrese su número de identificación");
        binding.tfEmail.setHint("Ingrese su email");
        binding.tvFooter.setText("Si ya tiene un usuario haga clic aquí");
        binding.button.setText("Registrarse");
    }
    private void setTextChangePassword(ActivityLoginBinding binding){
        binding.tvTitulo.setText("CAMBIAR CONTRASEÑA");
        binding.tvSubTitulo.setText("Fue enviada la contraseña a su email");
        binding.tfEmail.setHint("Ingrese su email");
        binding.tfOldPassword.setHint("Contraseña enviada por email");
        binding.tfNewPassword.setHint("Ingrese su nueva contraseña");
        binding.tfRepeatNewPassword.setHint("Repita su nueva contraseña");
        binding.button.setText("Cambiar contraseña");
    }
}