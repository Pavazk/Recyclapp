package com.example.isana.multiusos;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isana.R;

import java.security.MessageDigest;
import java.util.Arrays;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Control {
    public static String title_toast_exito = "¡Excelente!";
    public static String title_toast_fallo = "¡Oops!";
    public static void Intent(Activity activity, Class<?> claseLlegada) {
        activity.startActivity(new Intent(activity, claseLlegada));
        activity.finish();
    }

    public static String Recuperar(Activity activity, String name_registro, String campo) {
        SharedPreferences prefe = activity.getSharedPreferences(name_registro, MODE_PRIVATE);
        String texto = prefe.getString(campo, "");
        if (texto.length() != 0) {
            return texto;
        }
        return null;
    }

    public static void Guardar(Activity activity, String name_registro, String campo, String texto) {
        SharedPreferences preferencias = activity.getSharedPreferences(name_registro, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(campo, texto);
        editor.commit();
    }

    public static void ToastExito(Activity activity, String title, String txt) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.notificacion_exito, activity.findViewById(R.id.ll_exito));
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView titulo = layout.findViewById(R.id.tv_toast_exito_titulo);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView text = layout.findViewById(R.id.tv_toast_exito_txt);
        titulo.setText(title);
        text.setText(txt);
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void ToastFallo(Activity activity, String title, String txt) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.notificacion_fallo, activity.findViewById(R.id.ll_fallo));
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView titulo = layout.findViewById(R.id.tv_toast_fallo_titulo);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView text = layout.findViewById(R.id.tv_toast_fallo_txt);
        titulo.setText(title);
        text.setText(txt);
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static String getValuePreference(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences("mypref", MODE_PRIVATE);
        return preferences.getString(value, "");
    }

    public static void saveValuePreference(Context context, String value, String text) {
        SharedPreferences settings = context.getSharedPreferences("mypref", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString(value, text);
        editor.apply();
    }

    public static Retrofit getRetrofit(String url){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectionSpecs(Arrays.asList(
                        ConnectionSpec.CLEARTEXT,
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.COMPATIBLE_TLS))
                .build();

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static String IP(Context context){
        //return "http://"+getValuePreference(context, "ip")+":"+getValuePreference(context, "port")+"/scriptcase/app/Inventary_eggs/";
        return "http://"+getValuePreference(context, "ip")+":"+getValuePreference(context, "port")+"/";
    }
    public static String SHA256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            String sha256Hash = hexString.toString();
            System.out.println("Clave: "+ data);
            System.out.println("SHA-256 Hash: " + sha256Hash);
            return sha256Hash;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
