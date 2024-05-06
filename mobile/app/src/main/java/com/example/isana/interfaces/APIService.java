package com.example.isana.interfaces;

import com.example.isana.Inventory.InventoryAnswer;
import com.example.isana.InventoryHistory.InventoryHistoryAnswer;
import com.example.isana.login.EnvioLogin;
import com.example.isana.login.RespuestaLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
        @POST("inicio_sesion/")
        Call<RespuestaLogin> obtenerRespuestaLogin(@Body EnvioLogin envioLogin);

        @GET("read_inventory/")
        Call<List<InventoryAnswer>> getAnswerInventory();

        @GET("inventory_history/")
        Call<List<InventoryHistoryAnswer>> getAnswerInventoryHistory();
}
