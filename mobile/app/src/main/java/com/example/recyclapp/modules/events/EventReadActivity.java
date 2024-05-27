package com.example.recyclapp.modules.events;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recyclapp.common.Utils;
import com.example.recyclapp.databinding.ActivityEventReadBinding;
import com.example.recyclapp.modules.events.adapters.EventAdapter;
import com.example.recyclapp.modules.events.model.RegisterEvent;
import com.example.recyclapp.modules.menus.Home;

import java.util.ArrayList;
import java.util.List;

public class EventReadActivity extends AppCompatActivity {

    private List<RegisterEvent> listEvents;
    private EventAdapter eventAdapter;
    private ActivityEventReadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        initEvent();
    }

    private void initEvent() {
        listEvents = new ArrayList<>();
        eventAdapter = new EventAdapter(listEvents, position -> {
            //todo: colocar evento
        });
        binding.listEvents.setAdapter(eventAdapter);
        binding.listEvents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Utils.Intent(EventReadActivity.this, Home.class);
    }
}