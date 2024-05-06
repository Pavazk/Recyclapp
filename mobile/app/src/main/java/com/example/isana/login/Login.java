package com.example.isana.login;

import static com.example.isana.multiusos.Control.IP;
import static com.example.isana.multiusos.Control.SHA256;
import static com.example.isana.multiusos.Control.getValuePreference;
import static com.example.isana.multiusos.Control.saveValuePreference;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.isana.databinding.ActivityLoginBinding;
import com.example.isana.menus.Home;
import com.example.isana.multiusos.Control;
import com.example.isana.R;
import com.example.isana.interfaces.APIService;
import com.example.isana.multiusos.Person;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(binding);
        mostrarLogin(binding);
    }
    private void init(ActivityLoginBinding binding) {



        binding.usuario.setText(Control.Recuperar(this, "Credenciales", "user"));
        binding.btnLogin.setOnClickListener(view -> {
            if(String.valueOf(binding.usuario.getText()).isEmpty() || String.valueOf(binding.usuario.getText()) == null){
                Control.ToastFallo(Login.this, Control.title_toast_fallo, "Ingrese los campos");
            }else{
                if(String.valueOf(binding.clave.getText()).isEmpty() || String.valueOf(binding.clave.getText()) == null){
                    Control.ToastFallo(Login.this, Control.title_toast_fallo, "Ingrese los campos");
                }else{
                    Ingresar(binding);
                }
            }
        });
        binding.port.setText(getValuePreference(getApplicationContext(), "port"));
        binding.ip.setText(getValuePreference(getApplicationContext(), "ip"));
        binding.btnIp.setOnClickListener(view -> {
            if(binding.ip.getText().toString().isEmpty() || binding.ip.getText().toString().length()<6){
                if(binding.port.getText().toString().isEmpty() || binding.port.getText().toString().length()!=4){
                    Control.ToastFallo(Login.this, Control.title_toast_fallo, "Ingrese los campos");
                }else{
                    Control.ToastFallo(Login.this, Control.title_toast_fallo, "Ingrese IP");
                }
            }else{
                if(binding.port.getText().toString().isEmpty() || binding.port.getText().toString().length()!=4){
                    Control.ToastFallo(Login.this, Control.title_toast_fallo, "Ingrese el puerto");
                }else{
                    saveValuePreference(getApplicationContext(), "ip", binding.ip.getText().toString());
                    saveValuePreference(getApplicationContext(), "port", binding.port.getText().toString());
                    binding.ip.setText(getValuePreference(getApplicationContext(), "ip"));
                    binding.port.setText(getValuePreference(getApplicationContext(), "port"));
                    Control.ToastExito(Login.this, Control.title_toast_exito, "Se guardó la ip");
                    mostrarLogin(binding);
                }

            }
        });
        binding.settings.setOnClickListener(view -> {
            if(binding.tvLogin.getVisibility()==View.VISIBLE){
                mostrar(binding);
            }else{
                mostrarLogin(binding);
            }
        });

    }
    public void Ingresar(ActivityLoginBinding binding){
        MostrarCargar(binding);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectionSpecs(Arrays.asList(
                        ConnectionSpec.CLEARTEXT,
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.COMPATIBLE_TLS))
                .build();
        try{
            retrofit = new Retrofit.Builder()
                    .baseUrl(IP(this))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            GetLogin(new EnvioLogin(String.valueOf(binding.usuario.getText()), SHA256(String.valueOf(binding.clave.getText()))), binding);
        }catch (Exception e){
            Control.ToastFallo(this, Control.title_toast_fallo, "Ingrese IP/Puerto");
        }
    }

    public void GetLogin(EnvioLogin envioLogin, ActivityLoginBinding binding) {
        APIService service = retrofit.create(APIService.class);
        Call<RespuestaLogin> answerCall = service.obtenerRespuestaLogin(envioLogin);
        answerCall.enqueue(new Callback<RespuestaLogin>() {
            @Override
            public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
                if (response.isSuccessful()) {
                    RespuestaLogin respuestaLogin = response.body();
                    Log.e("Código de respuesta", String.valueOf(response.body()));
                    switch(respuestaLogin.getCodigo()){
                        case "00":
                            Person.setRol(respuestaLogin.getRol());
                            Person.setUser(envioLogin.getUser());
                            Control.Guardar(Login.this,"Credenciales", "user" ,Person.getUser());
                            Control.ToastExito(Login.this, Control.title_toast_exito, respuestaLogin.getEstado());
                            Control.Intent(Login.this, Home.class);
                            break;
                        case "01":
                            Control.ToastFallo(Login.this, Control.title_toast_fallo, respuestaLogin.getEstado());
                            break;
                        case "02":
                            Control.ToastFallo(Login.this, Control.title_toast_fallo, "Credenciales incorrectas");
                            break;
                        default:
                            Control.ToastFallo(Login.this, Control.title_toast_fallo,"Usuario no identificado");
                            break;
                    }
                } else {
                    Control.ToastFallo(Login.this, Control.title_toast_fallo, "Algo salió mal");
                    Log.e("Código de respuesta", String.valueOf(response.code()));
                }
                mostrarLogin(binding);
            }
            @Override
            public void onFailure(Call<RespuestaLogin> call, Throwable t) {
                Log.e("API", String.valueOf(t));
                Control.ToastFallo(Login.this, Control.title_toast_fallo,"Error API");
                mostrarLogin(binding);
            }
        });
    }
    @Override
    public void onBackPressed() {}
    public void mostrar(ActivityLoginBinding binding) {
        LayoutInflater inflater = this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.password_modal, this.findViewById(R.id.modal_window_pass));
        Button cancel = layout.findViewById(R.id.btn_cancel_modal_pass);
        Button accept = layout.findViewById(R.id.btn_accept_modal_pass);
        Dialog dialog = new Dialog(this);
        TextInputEditText textInputEditText = layout.findViewById(R.id.password_modal2);
        dialog.setContentView(layout);
        dialog.show();
        dialog.setCancelable(false);
        cancel.setOnClickListener(view -> dialog.dismiss());
        accept.setOnClickListener(view -> {
            String textoIngresado =textInputEditText.getText().toString();
            if (!textoIngresado.equals("")) {
                if (textoIngresado.equals("9876")) {
                    Control.ToastExito(Login.this, Control.title_toast_exito, "Bienvenido");
                    mostrarConfig(binding);
                    dialog.dismiss();
                } else {
                    Control.ToastFallo(Login.this, Control.title_toast_fallo, "Contraseña incorrecta");
                }
            }else {
                Control.ToastFallo(Login.this, Control.title_toast_fallo, "Ingrese contraseña");
            }
        });
    }



    public void mostrarConfig(ActivityLoginBinding binding){
        binding.tvLogin.setVisibility(View.GONE);
        binding.btnIp.setVisibility(View.GONE);
        binding.tfUsuario.setVisibility(View.GONE);
        binding.tfClave.setVisibility(View.GONE);
        binding.gBtnLogin.setVisibility(View.GONE);
        binding.settings.setVisibility(View.VISIBLE);
        binding.ivIcon.setVisibility(View.VISIBLE);
        binding.cargando.setVisibility(View.GONE);
        binding.settings.setVisibility(View.GONE);
        binding.tvIp.setVisibility(View.VISIBLE);
        binding.tfPort.setVisibility(View.VISIBLE);
        binding.tfIp.setVisibility(View.VISIBLE);
        binding.gBtnIp.setVisibility(View.VISIBLE);
        binding.btnIp.setVisibility(View.VISIBLE);

    }
    private void mostrarLogin(ActivityLoginBinding binding){
        binding.tvIp.setVisibility(View.GONE);
        binding.tfPort.setVisibility(View.GONE);
        binding.tfIp.setVisibility(View.GONE);
        binding.gBtnIp.setVisibility(View.GONE);
        binding.cargando.setVisibility(View.GONE);
        binding.ivIcon.setVisibility(View.VISIBLE);
        binding.tvLogin.setVisibility(View.VISIBLE);
        binding.tfUsuario.setVisibility(View.VISIBLE);
        binding.tfClave.setVisibility(View.VISIBLE);
        binding.gBtnLogin.setVisibility(View.VISIBLE);
        binding.settings.setVisibility(View.VISIBLE);
        binding.ivIcon.setVisibility(View.VISIBLE);
    }

    private void MostrarCargar(ActivityLoginBinding binding){
        binding.tvIp.setVisibility(View.GONE);
        binding.tfPort.setVisibility(View.GONE);
        binding.tfIp.setVisibility(View.GONE);
        binding.gBtnIp.setVisibility(View.GONE);
        binding.tvLogin.setVisibility(View.GONE);
        binding.btnIp.setVisibility(View.GONE);
        binding.tfUsuario.setVisibility(View.GONE);
        binding.tfClave.setVisibility(View.GONE);
        binding.gBtnLogin.setVisibility(View.GONE);
        binding.settings.setVisibility(View.GONE);
        binding.ivIcon.setVisibility(View.GONE);
        binding.cargando.setVisibility(View.VISIBLE);
    }
}