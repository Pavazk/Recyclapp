package com.example.recyclapp.modules.main.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.recyclapp.common.Utils;
import com.example.recyclapp.databinding.ActivityMainBinding;
import com.example.recyclapp.modules.main.presenter.MainPresenter;

import java.util.concurrent.Executor;

public class MainView extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new MainPresenter(this).init(binding);
        loadCredentialsByBiometric();
    }

    public void loadCredentialsByBiometric(){
        String email = Utils.restore(MainView.this, "email");
        String password = Utils.restore(MainView.this, "password");
        BiometricManager biometricManager = BiometricManager.from(this);
        if(biometricManager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS || email.isEmpty() || password.isEmpty()){
            return;
        }
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación biométrica")
                .setSubtitle("Inicia sesión usando tu huella dactilar")
                .setNegativeButtonText("Usar contraseña")
                .build();

        BiometricPrompt biometricPrompt = new BiometricPrompt(MainView.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // Handle error case
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                binding.etEmail.setText(email);
                binding.etOldPassword.setText(password);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // Handle failure case
            }
        });
        biometricPrompt.authenticate(promptInfo);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }
}