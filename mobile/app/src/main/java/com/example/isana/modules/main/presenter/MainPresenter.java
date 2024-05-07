package com.example.isana.modules.main.presenter;

import android.view.View;

import com.example.isana.databinding.ActivityMainBinding;
import com.example.isana.modules.main.model.MainModel;
import com.example.isana.modules.main.view.MainView;

public class MainPresenter {

    private final MainModel model;

    public MainPresenter(MainView mainView) {
        this.model = new MainModel(this, mainView);
    }

    public void init(ActivityMainBinding binding) {
        showLogin(binding);
        onClick(binding);
        rules(binding);
    }

    public void showLoading(ActivityMainBinding binding) {
        binding.cargando.setVisibility(View.VISIBLE);
        binding.ivSettings.setVisibility(View.GONE);
        binding.ivIcon.setVisibility(View.GONE);
        binding.tvTitulo.setVisibility(View.GONE);
        binding.tvSubTitulo.setVisibility(View.GONE);
        binding.linearLayout.setVisibility(View.GONE);
        binding.tvFooter.setVisibility(View.GONE);
        binding.button.setVisibility(View.GONE);
    }

    public void showLogin(ActivityMainBinding binding) {
        binding.cargando.setVisibility(View.GONE);
        binding.ivSettings.setVisibility(View.VISIBLE);
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
        binding.tfIp.setVisibility(View.GONE);

        binding.tvFooter.setVisibility(View.VISIBLE);
        binding.button.setVisibility(View.VISIBLE);

        setTextLogin(binding);
    }

    public void showRegister(ActivityMainBinding binding) {
        binding.cargando.setVisibility(View.GONE);
        binding.ivSettings.setVisibility(View.VISIBLE);
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
        binding.tfIp.setVisibility(View.GONE);

        binding.tvFooter.setVisibility(View.VISIBLE);
        binding.button.setVisibility(View.VISIBLE);

        setTextRegister(binding);
    }

    public void showChangePassword(ActivityMainBinding binding) {
        binding.cargando.setVisibility(View.GONE);
        binding.ivSettings.setVisibility(View.GONE);
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
        binding.tfIp.setVisibility(View.GONE);

        binding.tvFooter.setVisibility(View.GONE);
        binding.button.setVisibility(View.VISIBLE);

        setTextChangePassword(binding);
    }

    public void showIp(ActivityMainBinding binding) {
        binding.cargando.setVisibility(View.GONE);
        binding.ivSettings.setVisibility(View.GONE);
        binding.ivIcon.setVisibility(View.VISIBLE);
        binding.tvTitulo.setVisibility(View.VISIBLE);
        binding.tvSubTitulo.setVisibility(View.GONE);

        binding.linearLayout.setVisibility(View.VISIBLE);
        binding.tfName.setVisibility(View.GONE);
        binding.tfCode.setVisibility(View.GONE);
        binding.tfIdentification.setVisibility(View.GONE);
        binding.tfEmail.setVisibility(View.GONE);

        binding.tfOldPassword.setVisibility(View.GONE);
        binding.tfNewPassword.setVisibility(View.GONE);
        binding.tfRepeatNewPassword.setVisibility(View.GONE);
        binding.tfIp.setVisibility(View.VISIBLE);

        binding.tvFooter.setVisibility(View.GONE);
        binding.button.setVisibility(View.VISIBLE);

        setTextIp(binding);
    }

    private void rules(ActivityMainBinding binding) {
        binding.etName.setSingleLine(true);
        binding.etCode.setSingleLine(true);
        binding.etIdentification.setSingleLine(true);
        binding.etEmail.setSingleLine(true);
        binding.etOldPassword.setSingleLine(true);
        binding.etNewPassword.setSingleLine(true);
        binding.etRepeatNewPassword.setSingleLine(true);
    }

    private void onClick(ActivityMainBinding binding) {
        model.onClick(binding);
    }

    private void setTextLogin(ActivityMainBinding binding) {
        binding.tvTitulo.setText("INICIO DE SESIÓN");
        binding.tfEmail.setHint("Ingrese su email");
        binding.tfOldPassword.setHint("Ingrese su contraseña");
        binding.tvFooter.setText("Si no tiene un usuario haga clic aquí");
        binding.button.setText("Ingresar");
    }

    private void setTextRegister(ActivityMainBinding binding) {
        binding.tvTitulo.setText("REGISTRO DE USUARIO");
        binding.tfName.setHint("Ingrese su nombre");
        binding.tfCode.setHint("Ingrese su código");
        binding.tfIdentification.setHint("Ingrese su número de identificación");
        binding.tfEmail.setHint("Ingrese su email");
        binding.tvFooter.setText("Si ya tiene un usuario haga clic aquí");
        binding.button.setText("Registrarse");
    }

    private void setTextChangePassword(ActivityMainBinding binding) {
        binding.tvTitulo.setText("CAMBIAR CONTRASEÑA");
        binding.tvSubTitulo.setText("Fue enviada la contraseña a su email");
        binding.tfEmail.setHint("Ingrese su email");
        binding.tfOldPassword.setHint("Contraseña enviada por email");
        binding.tfNewPassword.setHint("Ingrese su nueva contraseña");
        binding.tfRepeatNewPassword.setHint("Repita su nueva contraseña");
        binding.button.setText("Cambiar contraseña");
    }

    private void setTextIp(ActivityMainBinding binding) {
        binding.tvTitulo.setText("INGRESE LA IP");
        binding.tfIp.setHint("Ingrese la ip");
        binding.button.setText("Guardar");
    }
}
