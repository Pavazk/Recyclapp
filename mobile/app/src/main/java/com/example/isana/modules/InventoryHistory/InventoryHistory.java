package com.example.isana.modules.InventoryHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.isana.R;
import com.example.isana.common.Utils;

import java.util.Arrays;
import java.util.List;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class InventoryHistory extends AppCompatActivity {
    Retrofit retrofit;
    RecyclerView recyclerView;
    List<InventoryHistoryAnswer> inventoryHistoryAnswers;
    InventoryHistoryAdapter2 inventoryHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_history);
        retro();
        ApiConnection();
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
                .baseUrl(Utils.IP(this))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        recyclerView = findViewById(R.id.recyclerview3);
    }
    public void ApiConnection() {
        /*APIService service = retrofit.create(APIService.class);
        Call<List<InventoryHistoryAnswer>> inventoryHistoryAnswerCall = service.getAnswerInventoryHistory();
        inventoryHistoryAnswerCall.enqueue(new Callback<List<InventoryHistoryAnswer>>() {
            @Override
            public void onResponse(@NonNull Call<List<InventoryHistoryAnswer>> call, @NonNull Response<List<InventoryHistoryAnswer>> response) {
                if (response.isSuccessful()) {
                    Map<String, List<InventoryHistoryAnswer>> mapaDeFechas = new HashMap<>();
                    for (InventoryHistoryAnswer objeto : response.body()) {
                        String fecha = objeto.getDate_audit_inv().split(" ")[0];
                        // Verifica si el mapa ya contiene la fecha como clave
                        if (mapaDeFechas.containsKey(fecha)) {
                            mapaDeFechas.get(fecha).add(objeto);
                        } else {
                            // Si la fecha no está en el mapa, crea una nueva lista y agrega el objeto
                            List<InventoryHistoryAnswer> nuevaLista = new ArrayList<>();
                            nuevaLista.add(objeto);
                            mapaDeFechas.put(fecha, nuevaLista);
                        }
                    }

                    inventoryHistoryAnswers = response.body();
                    inventoryHistoryAdapter = new InventoryHistoryAdapter2(InventoryHistory.this, mapaDeFechas, InventoryHistory.this);
                    recyclerView.setAdapter(inventoryHistoryAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(InventoryHistory.this));
                    Log.e("AnswerInv", "si ta bien: " + response.body());

                } else {
                    Utils.ToastFallo(InventoryHistory.this, Utils.title_toast_fallo, "Error plataforma!");
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<InventoryHistoryAnswer>> call, @NonNull Throwable t) {
                Utils.ToastFallo(InventoryHistory.this, Utils.title_toast_fallo, "Error plataforma!");
                finish();
            }
        });*/
    }



    public void backButton(View view) {
        finish();
    }

}