package com.example.recyclapp.modules.menus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.recyclapp.modules.Inventory.Inventory;
import com.example.recyclapp.modules.InventoryHistory.InventoryHistory;
import com.example.recyclapp.common.Utils;
import com.example.recyclapp.modules.init.InitView;
import com.example.recyclapp.R;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<item_menu> data;
    
    private final String BINS = "Canecas";
    private final String PRODUCTS = "Productos";
    private final String EVENTS = "Eventos";
    private final String PERSONS = "Personas";
    private final String ADD_PRODUCTS = "Agregar productos";
    private final String UPDATE_PRODUCTS = "Actualizar productos";
    private final String READ_PRODUCTS = "Ver productos";
    private final String DELETE_PRODUCTS = "Eliminar productos";
    private final String ADD_BINS = "Agregar canecas";
    private final String UPDATE_BINS = "Actualizar canecas";
    private final String READ_BINS = "Ver canecas";
    private final String DELETE_BINS = "Eliminar canecas";
    private final String ADD_PERSONS = "Agregar personas";
    private final String UPDATE_PERSONS = "Actualizar personas";
    private final String READ_PERSONS = "Ver personas";
    private final String DELETE_PERSONS = "Eliminar personas";
    private final String ADD_EVENTS = "Agregar eventos";
    private final String UPDATE_EVENTS = "Actualizar eventos";
    private final String READ_EVENTS = "Ver eventos";
    private final String DELETE_EVENTS = "Eliminar eventos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recycler();
    }

    private void recycler() {
        recyclerView = findViewById(R.id.recyclerview);
        data = new ArrayList<>();
        data.add(new item_menu(BINS, R.mipmap.bins));
        data.add(new item_menu(PRODUCTS, R.mipmap.products));
        data.add(new item_menu(EVENTS, R.mipmap.events));
        data.add(new item_menu(PERSONS, R.mipmap.persons));
        adapter = new MyAdapter(this, data, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, data.size() > 1 ? 2 : 1));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onItemClick(int position) {
        switch (data.get(position).getText()) {
            case BINS:
                data = new ArrayList<>();
                data.add(new item_menu(ADD_BINS, R.mipmap.icon_add_bins));
                data.add(new item_menu(UPDATE_BINS, R.mipmap.icon_update_bins));
                data.add(new item_menu(READ_BINS, R.mipmap.icon_read_bins));
                data.add(new item_menu(DELETE_BINS, R.mipmap.icon_delete_bins));
                adapter.updateData(data);
                break;
            case ADD_BINS:
                break;
            case UPDATE_BINS:
                break;
            case READ_BINS:
                break;
            case DELETE_BINS:
                break;
            case PRODUCTS:
                data = new ArrayList<>();
                data.add(new item_menu(ADD_PRODUCTS, R.mipmap.icon_add_products));
                data.add(new item_menu(UPDATE_PRODUCTS, R.mipmap.icon_update_products));
                data.add(new item_menu(READ_PRODUCTS, R.mipmap.icon_read_products));
                data.add(new item_menu(DELETE_PRODUCTS, R.mipmap.icon_delete_products));
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
                data = new ArrayList<>();
                data.add(new item_menu(ADD_EVENTS, R.mipmap.icon_add_event));
                data.add(new item_menu(UPDATE_EVENTS, R.mipmap.icon_update_event));
                data.add(new item_menu(READ_EVENTS, R.mipmap.icon_read_event));
                data.add(new item_menu(DELETE_EVENTS, R.mipmap.icon_delete_event));
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
                data = new ArrayList<>();
                data.add(new item_menu(ADD_PERSONS, R.mipmap.icon_add_persons));
                data.add(new item_menu(UPDATE_PERSONS, R.mipmap.icon_update_persons));
                data.add(new item_menu(READ_PERSONS, R.mipmap.icon_read_persons));
                data.add(new item_menu(DELETE_PERSONS, R.mipmap.icon_delete_persons));
                adapter.updateData(data);
                break;
            case ADD_PERSONS:
                break;
            case UPDATE_PERSONS:
                break;
            case READ_PERSONS:
                break;
            case DELETE_PERSONS:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        mostrarDialogo();
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