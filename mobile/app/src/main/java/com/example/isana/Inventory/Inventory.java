package com.example.isana.Inventory;

import static com.example.isana.multiusos.Control.getValuePreference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.isana.R;
import com.example.isana.interfaces.APIService;
import com.example.isana.login.Login;
import com.example.isana.multiusos.Control;

import java.util.Arrays;
import java.util.List;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Inventory extends AppCompatActivity {
    Retrofit retrofit;
    private RecyclerView recyclerView;
    private ReadInventoryAdapter readInventoryAdapter;
    List<InventoryAnswer> inventoryAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventary);
        init();
        retro();
    }
    private void init(){
        recyclerView = findViewById(R.id.recyclerview2);
    }
    private void retro(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectionSpecs(Arrays.asList(
                        ConnectionSpec.CLEARTEXT,
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.COMPATIBLE_TLS))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Control.IP(this))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        ApiConnection();
    }

    public void ApiConnection() {
        APIService service = retrofit.create(APIService.class);
        Call<List<InventoryAnswer>> inventoryAnswerCall = service.getAnswerInventory();
        inventoryAnswerCall.enqueue(new Callback<List<InventoryAnswer>>() {
            @Override
            public void onResponse(@NonNull Call<List<InventoryAnswer>> call, @NonNull Response<List<InventoryAnswer>> response) {
                if (response.isSuccessful()) {
                    inventoryAnswers = response.body();
                    readInventoryAdapter = new ReadInventoryAdapter(inventoryAnswers, Inventory.this);
                    recyclerView.setAdapter(readInventoryAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Inventory.this));
                } else {
                    Log.e("AnswerInv", "Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<InventoryAnswer>> call, @NonNull Throwable t) {
                Log.e("AnswerInv", "onFailure: " + t);
            }
        });
    }
    public void backButton(View view) {
        finish();
    }
}