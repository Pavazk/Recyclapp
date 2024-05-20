package com.example.recyclapp.modules.menus;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.recyclapp.R;
import com.example.recyclapp.common.Utils;
import com.example.recyclapp.databinding.ActivityHomeBinding;
import com.example.recyclapp.modules.bins.BinAddActivity;
import com.example.recyclapp.modules.bins.BinReadActivity;
import com.example.recyclapp.modules.init.InitView;
import com.example.recyclapp.modules.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    private MyAdapter adapter;
    private List<ItemMenu> data;

    private final String BINS = "Canecas";
    private final String PRODUCTS = "Productos";
    private final String EVENTS = "Eventos";
    private final String PERSONS = "Perfil";
    private final String ADD_PRODUCTS = "Agregar productos";
    private final String UPDATE_PRODUCTS = "Actualizar productos";
    private final String READ_PRODUCTS = "Ver productos";
    private final String DELETE_PRODUCTS = "Eliminar productos";
    private final String ADD_BINS = "Agregar canecas";
    private final String UPDATE_BINS = "Actualizar canecas";
    private final String READ_BINS = "Ver canecas";
    private final String DELETE_BINS = "Eliminar canecas";
    private final String ADD_PERSONS = "Agregar personas";
    private final String UPDATE_PERSONS = "Actualizar perfil";
    private final String READ_PERSONS = "Ver perfil";
    private final String DELETE_PERSONS = "Eliminar perfil";
    private final String ADD_EVENTS = "Agregar eventos";
    private final String UPDATE_EVENTS = "Actualizar eventos";
    private final String READ_EVENTS = "Ver eventos";
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
        adapter = new MyAdapter(this, data, this);
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerview.setNestedScrollingEnabled(false);
        binding.recyclerview.setFocusable(false);
        binding.recyclerview.setHasFixedSize(true);
        showHome();
    }

    private void showHome() {
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
        switch (data.get(position).getText()) {
            case BINS:
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
                    Utils.Intent(this, BinAddActivity.class);
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
                binding.tvTitulo.setText(PRODUCTS.toUpperCase());
                data = new ArrayList<>();
                data.add(new ItemMenu(ADD_PRODUCTS, R.mipmap.icon_add_products));
                data.add(new ItemMenu(UPDATE_PRODUCTS, R.mipmap.icon_update_products));
                data.add(new ItemMenu(READ_PRODUCTS, R.mipmap.icon_read_products));
                data.add(new ItemMenu(DELETE_PRODUCTS, R.mipmap.icon_delete_products));
                adapter.updateData(data);
                break;
            case ADD_PRODUCTS:
                break;
            case UPDATE_PRODUCTS:
                break;
            case READ_PRODUCTS:
                break;
            case DELETE_PRODUCTS:
                break;
            case EVENTS:
                binding.tvTitulo.setText(EVENTS.toUpperCase());
                data = new ArrayList<>();
                data.add(new ItemMenu(ADD_EVENTS, R.mipmap.icon_add_event));
                data.add(new ItemMenu(UPDATE_EVENTS, R.mipmap.icon_update_event));
                data.add(new ItemMenu(READ_EVENTS, R.mipmap.icon_read_event));
                data.add(new ItemMenu(DELETE_EVENTS, R.mipmap.icon_delete_event));
                adapter.updateData(data);
                break;
            case ADD_EVENTS:
                break;
            case UPDATE_EVENTS:
                break;
            case READ_EVENTS:
                break;
            case DELETE_EVENTS:
                break;
            case PERSONS:
                Utils.Intent(this, ProfileActivity.class);
                break;
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if("BIENVENIDO".equals(binding.tvTitulo.getText().toString())){
            mostrarDialogo();
            return;
        }
        showHome();
    }

    private void mostrarDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogStyleLight);
        builder.setTitle("IMPORTANTE");
        builder.setMessage("¿Deseas cerrar sesión?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.Intent(Home.this, InitView.class);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}