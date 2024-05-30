package com.example.recyclapp.modules.menus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.recyclapp.R;
import com.example.recyclapp.common.Utils;
import com.example.recyclapp.databinding.ActivityHomeBinding;
import com.example.recyclapp.modules.bins.BinAddActivity;
import com.example.recyclapp.modules.bins.BinReadActivity;
import com.example.recyclapp.modules.events.EventActivity;
import com.example.recyclapp.modules.events.EventReadActivity;
import com.example.recyclapp.modules.init.InitView;
import com.example.recyclapp.modules.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements HomeAdapter.OnItemClickListener {

    private HomeAdapter adapter;
    private List<ItemMenu> data;

    private final String BINS = "Canecas";
    private final String PRODUCTS = "Productos";
    private final String EVENTS = "Eventos";
    private final String PERSONS = "Perfil";
    private final String WATCH_EVENTS = "Ver eventos";
    private final String ADD_BINS = "Agregar canecas";
    private final String UPDATE_BINS = "Actualizar canecas";
    private final String READ_BINS = "Ver canecas";
    private final String DELETE_BINS = "Eliminar canecas";
    private final String ADD_EVENTS = "Agregar eventos";
    private final String UPDATE_EVENTS = "Actualizar eventos";
    private final String READ_EVENTS = "Ver eventos";
    private final String READ_MY_EVENTS = "Mis eventos";
    private final String READ_OTHER_EVENTS = "Otros eventos";
    private final String DELETE_EVENTS = "Eliminar eventos";

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView(binding);
    }

    private void initView(ActivityHomeBinding binding) {
        data = new ArrayList<>();
        adapter = new HomeAdapter(data, this);
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerview.setNestedScrollingEnabled(false);
        binding.recyclerview.setFocusable(false);
        binding.recyclerview.setHasFixedSize(true);
        binding.ivBack.setOnClickListener(view ->{
            showHome();
        });
        showHome();
    }

    private void showHome() {
        binding.ivBack.setVisibility(View.GONE);
        binding.tvTitulo.setText("BIENVENIDO");
        data = new ArrayList<>();
        data.add(new ItemMenu(BINS, R.mipmap.bins));
        data.add(new ItemMenu(PRODUCTS, R.mipmap.products));
        data.add(new ItemMenu(EVENTS, R.mipmap.events));
        data.add(new ItemMenu(PERSONS, R.mipmap.persons));
        adapter.updateData(data);
    }

    @Override
    public void onItemClick(int position) {
        System.gc();
        switch (data.get(position).getText()) {
            case BINS:
                binding.ivBack.setVisibility(View.VISIBLE);
                data = new ArrayList<>();
                binding.tvTitulo.setText(BINS.toUpperCase());
                data.add(new ItemMenu(ADD_BINS, R.mipmap.icon_add_bins));
                data.add(new ItemMenu(UPDATE_BINS, R.mipmap.icon_update_bins));
                data.add(new ItemMenu(READ_BINS, R.mipmap.icon_read_bins));
                data.add(new ItemMenu(DELETE_BINS, R.mipmap.icon_delete_bins));
                adapter.updateData(data);
                break;
            case ADD_BINS:
                if (Utils.havePermission(this)) {
                    Utils.IntentWFinish(this, BinAddActivity.class);
                } else {
                    Utils.permissionManager(this);
                }
                break;
            case UPDATE_BINS:
                Intent intent1 = new Intent(this, BinReadActivity.class);
                intent1.putExtra("case", "UPDATE");
                startActivity(intent1);
                finish();
                break;
            case READ_BINS:
                startActivity(new Intent(this, BinReadActivity.class));
                finish();
                break;
            case DELETE_BINS:
                Intent intent2 = new Intent(this, BinReadActivity.class);
                intent2.putExtra("case", "DELETE");
                startActivity(intent2);
                finish();
                break;
            case PRODUCTS:
                //todo: hacer productos
                break;
            case EVENTS:
                binding.ivBack.setVisibility(View.VISIBLE);
                binding.tvTitulo.setText(EVENTS.toUpperCase());
                data = new ArrayList<>();
                data.add(new ItemMenu(ADD_EVENTS, R.mipmap.icon_add_event));
                data.add(new ItemMenu(UPDATE_EVENTS, R.mipmap.icon_update_event));
                data.add(new ItemMenu(READ_EVENTS, R.mipmap.icon_read_event));
                data.add(new ItemMenu(DELETE_EVENTS, R.mipmap.icon_delete_event));
                adapter.updateData(data);
                break;
            case ADD_EVENTS:
                Utils.IntentWFinish(this, EventActivity.class);
                break;
            case UPDATE_EVENTS:
                Intent intent6 = new Intent(this, EventReadActivity.class);
                intent6.putExtra("case", "UPDATE_EVENTS");
                startActivity(intent6);
                finish();
                break;
            case READ_EVENTS:
                binding.ivBack.setVisibility(View.VISIBLE);
                binding.tvTitulo.setText(WATCH_EVENTS.toUpperCase());
                data = new ArrayList<>();
                data.add(new ItemMenu(READ_MY_EVENTS, R.mipmap.icon_update_persons));
                data.add(new ItemMenu(READ_OTHER_EVENTS, R.mipmap.icon_delete_persons));
                adapter.updateData(data);
                break;
            case DELETE_EVENTS:
                Intent intent5 = new Intent(this, EventReadActivity.class);
                intent5.putExtra("case", "DELETE_EVENTS");
                startActivity(intent5);
                finish();
                break;
            case PERSONS:
                Utils.IntentWFinish(this, ProfileActivity.class);
                break;
            case READ_MY_EVENTS:
                Intent intent3 = new Intent(this, EventReadActivity.class);
                intent3.putExtra("case", "READ_MY_EVENTS");
                startActivity(intent3);
                finish();
                break;
            case READ_OTHER_EVENTS:
                Intent intent4 = new Intent(this, EventReadActivity.class);
                intent4.putExtra("case", "READ_OTHER_EVENTS");
                startActivity(intent4);
                finish();
                break;
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if("BIENVENIDO".equals(binding.tvTitulo.getText().toString())){
            showDialog();
            return;
        }
        showHome();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogStyleLight);
        builder.setTitle("IMPORTANTE");
        builder.setMessage("¿Deseas cerrar sesión?");
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            Utils.IntentWFinish(Home.this, InitView.class);
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}