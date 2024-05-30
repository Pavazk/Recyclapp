package com.example.recyclapp.modules.events;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recyclapp.common.Utils;
import com.example.recyclapp.common.interfaces.APIService;
import com.example.recyclapp.databinding.ActivityEventReadBinding;
import com.example.recyclapp.modules.events.adapters.EventAdapter;
import com.example.recyclapp.modules.events.model.Event;
import com.example.recyclapp.modules.menus.Home;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventReadActivity extends AppCompatActivity {

    private List<Event> listEvents;
    private EventAdapter eventAdapter;
    private ActivityEventReadBinding binding;
    private String bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        bundle = "";
        if (extras != null && extras.containsKey("case")) {
            bundle = extras.getString("case");
        }
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        listEvents = new ArrayList<>();
        eventAdapter = new EventAdapter(listEvents, position -> {
            if ("DELETE_EVENTS".equals(bundle)) {
                showAlertDialog(listEvents.get(position), false);
            } else if ("UPDATE_EVENTS".equals(bundle)) {
                showAlertDialog(listEvents.get(position), true);
            }
        });
        binding.listEvents.setAdapter(eventAdapter);
        binding.listEvents.setLayoutManager(new LinearLayoutManager(EventReadActivity.this, LinearLayoutManager.VERTICAL, false));
        APIService service = Utils.getRetrofit(this).create(APIService.class);
        if ("READ_OTHER_EVENTS".equals(bundle)){
            service.getAllEventsByUser(Utils.restore(this, Utils.KEY_CODE)).enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    if (response.isSuccessful()) {
                        listEvents = response.body();
                        eventAdapter.updateData(listEvents);
                    } else {
                        Toast.makeText(EventReadActivity.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Event>> call, Throwable t) {
                    Toast.makeText(EventReadActivity.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        service.getAllEventsByOwner(Utils.restore(this, Utils.KEY_CODE)).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    listEvents = response.body();
                    eventAdapter.updateData(listEvents);
                } else {
                    Toast.makeText(EventReadActivity.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(EventReadActivity.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showAlertDialog(Event event, boolean isUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("IMPORTANTE");
        builder.setMessage("Deseas " + (isUpdate ? "actualizar" : "eliminar") + " el evento seleccionado?");
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            if (isUpdate) {
                Intent intent4 = new Intent(this, EventActivity.class);
                intent4.putExtra("case", event.getId());
                startActivity(intent4);
                finish();
                return;
            }
            try {
                APIService service = Utils.getRetrofit(EventReadActivity.this).create(APIService.class);
                service.deleteEvent(event).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(EventReadActivity.this, "Evento eliminado", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(EventReadActivity.this, "Evento eliminado", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
            } catch (Throwable t) {
                t.printStackTrace();
                onBackPressed();
                Toast.makeText(EventReadActivity.this, "Evento eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Utils.Intent(EventReadActivity.this, Home.class);
    }
}