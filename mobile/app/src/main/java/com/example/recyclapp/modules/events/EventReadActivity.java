package com.example.recyclapp.modules.events;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recyclapp.common.Utils;
import com.example.recyclapp.common.interfaces.APIService;
import com.example.recyclapp.databinding.ActivityEventReadBinding;
import com.example.recyclapp.modules.events.adapters.EventAdapter;
import com.example.recyclapp.modules.events.model.Event;
import com.example.recyclapp.modules.events.model.RegisterEvent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        listEvents = new ArrayList<>();
        eventAdapter = new EventAdapter(listEvents, position -> {
        });
        binding.listEvents.setAdapter(eventAdapter);
        binding.listEvents.setLayoutManager(new LinearLayoutManager(EventReadActivity.this, LinearLayoutManager.VERTICAL, false));

        APIService service = Utils.getRetrofit(this).create(APIService.class);
        service.getAllEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()){
                    listEvents = response.body();
                    eventAdapter.updateData(listEvents);
                }else{
                    Toast.makeText(EventReadActivity.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(EventReadActivity.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Utils.Intent(EventReadActivity.this, Home.class);
    }
}