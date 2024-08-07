package com.example.recyclapp.modules.main.model;

import static com.example.recyclapp.common.Utils.SHA256;

import android.widget.Toast;

import com.example.recyclapp.databinding.ActivityMainBinding;
import com.example.recyclapp.common.interfaces.APIService;
import com.example.recyclapp.modules.main.data.ResponseFailure;
import com.example.recyclapp.modules.init.InitView;
import com.example.recyclapp.modules.menus.Home;
import com.example.recyclapp.modules.main.data.User;
import com.example.recyclapp.modules.main.data.UserLogin;
import com.example.recyclapp.modules.main.data.UserUpdate;
import com.example.recyclapp.modules.main.presenter.MainPresenter;
import com.example.recyclapp.modules.main.view.MainView;
import com.example.recyclapp.common.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel {
    private final MainPresenter presenter;
    private final MainView mainView;

    public MainModel(MainPresenter presenter, MainView mainView) {
        this.presenter = presenter;
        this.mainView = mainView;
    }

    public void onClick(ActivityMainBinding binding) {
        binding.ivSettings.setOnClickListener(view -> {
            presenter.showIp(binding);
        });
        binding.tvFooter.setOnClickListener(view -> {
            switch (binding.tvTitulo.getText().toString()) {
                case "INICIO DE SESIÓN":
                    presenter.showRegister(binding);
                    break;
                case "REGISTRO DE USUARIO":
                    presenter.showLogin(binding);
            }
        });
        binding.button.setOnClickListener(view -> {
            presenter.showLoading(binding);
            if (InitView.isOffline) {
                Utils.save(mainView, "email", "leospinap@ufpso.edu.co");
                Utils.save(mainView, "password", "123456");
                Utils.IntentWFinish(mainView, Home.class);
                return;
            }
            switch (binding.tvTitulo.getText().toString()) {
                case "INICIO DE SESIÓN":
                    loginUser(binding);
                    break;
                case "REGISTRO DE USUARIO":
                    registerUser(binding);
                    break;
                case "CAMBIAR CONTRASEÑA":
                    changePasswordUser(binding);
                    break;
                case "INGRESE LA IP":
                    saveIp(binding);
            }
        });
    }

    private void registerUser(ActivityMainBinding binding) {
        String name = binding.etName.getText().toString();
        String code = binding.etCode.getText().toString();
        String identification = binding.etIdentification.getText().toString();
        String email = binding.etEmail.getText().toString();
        User user = new User(email, code, name, identification);
        try {
            APIService service = Utils.getRetrofit(mainView).create(APIService.class);
            Call<User> answerCall = service.registerUser(user);
            answerCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        presenter.showChangePassword(binding);
                    } else {
                        try {
                            presenter.showError(new ObjectMapper().readValue(response.errorBody().string(), ResponseFailure.class), binding);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mainView, "Algo salió mal!", Toast.LENGTH_SHORT).show();
                        }
                        presenter.showRegister(binding);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    presenter.showRegister(binding);
                    Toast.makeText(mainView, "FALLÓ", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            presenter.showRegister(binding);
            Toast.makeText(mainView, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    private void changePasswordUser(ActivityMainBinding binding) {
        String email = binding.etEmail.getText().toString();
        String oldPassword = binding.etOldPassword.getText().toString();
        String newPassword = binding.etNewPassword.getText().toString();
        String repeatNewPassword = binding.etRepeatNewPassword.getText().toString();
        UserUpdate user = new UserUpdate(email, oldPassword, SHA256(newPassword), SHA256(repeatNewPassword));
        try {
            APIService service = Utils.getRetrofit(mainView).create(APIService.class);
            Call<User> answerCall = service.updateUser(user);
            answerCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        Utils.save(mainView, Utils.KEY_CODE, user.getCode());
                        Utils.save(mainView, Utils.KEY_ROLE, user.getUserType().getName());
                        Utils.save(mainView, "email", user.getEmail());
                        Utils.save(mainView, "password", newPassword);
                        Utils.IntentWFinish(mainView, Home.class);
                        presenter.clearFields(binding);
                    } else {
                        try {
                            presenter.showError(new ObjectMapper().readValue(response.errorBody().string(), ResponseFailure.class), binding);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(mainView, "Por favor valida los campos", Toast.LENGTH_SHORT).show();
                        presenter.showChangePassword(binding);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(mainView, "FALLÓ", Toast.LENGTH_SHORT).show();
                    presenter.showChangePassword(binding);
                }
            });
        } catch (Exception e) {
            Toast.makeText(mainView, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
            presenter.showChangePassword(binding);
        }
    }

    private void loginUser(ActivityMainBinding binding) {
        String email = binding.etEmail.getText().toString();
        String password = binding.etOldPassword.getText().toString();
        UserLogin user = new UserLogin(email, password);
        try {
            APIService service = Utils.getRetrofit(mainView).create(APIService.class);
            Call<User> answerCall = service.loginUser(user);
            answerCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        if(user != null && user.getPassword() != null && user.getPassword().length() <=8){
                            presenter.showChangePassword(binding);
                            return;
                        }
                        Utils.save(mainView, Utils.KEY_CODE, user.getCode());
                        Utils.save(mainView, Utils.KEY_ROLE, user.getUserType().getName());
                        Utils.save(mainView, "email", user.getEmail());
                        Utils.save(mainView, "password", password);
                        Utils.IntentWFinish(mainView, Home.class);
                        presenter.clearFields(binding);
                    } else {
                        try {
                            presenter.showError(new ObjectMapper().readValue(response.errorBody().string(), ResponseFailure.class), binding);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(mainView, "Por favor valida los campos", Toast.LENGTH_SHORT).show();
                        presenter.showLogin(binding);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(mainView, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                    presenter.showLogin(binding);
                }
            });

        } catch (Exception e) {
            Toast.makeText(mainView, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
            presenter.showLogin(binding);
        }
    }

    private void intent2Home(ActivityMainBinding binding){

    }

    private void saveIp(ActivityMainBinding binding) {
        String ip = binding.etIp.getText().toString();
        Utils.save(mainView, "ip", ip);
        presenter.clearFields(binding);
        presenter.showLogin(binding);
    }

}
