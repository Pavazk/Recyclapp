package com.example.isana.menus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.isana.Inventory.Inventory;
import com.example.isana.InventoryHistory.InventoryHistory;
import com.example.isana.multiusos.Control;
import com.example.isana.inicio.MainActivity;
import com.example.isana.R;
import com.example.isana.multiusos.Person;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements MyAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<item_menu> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recycler();
    }

    private void recycler(){
        recyclerView = findViewById(R.id.recyclerview);
        data = new ArrayList<>();
        switch(Person.getRol()){
            case "Super_admin":
                data.add(new item_menu("Ver inventario", R.drawable.inventario));
                data.add(new item_menu("Historial inventario", R.drawable.historia_inventario));
                data.add(new item_menu("Registro de pagos", R.drawable.registro_pagos));
                data.add(new item_menu("Registrar pedidos", R.drawable.pedidos));
                break;
            case "Trabajador":
                data.add(new item_menu("Ver inventario", R.drawable.inventario));
                data.add(new item_menu("Historial inventario", R.drawable.historia_inventario));
                break;
            case "Gerente":
                data.add(new item_menu("Ver inventario", R.drawable.inventario));
                data.add(new item_menu("Historial inventario", R.drawable.historia_inventario));
                data.add(new item_menu("Registro de pagos", R.drawable.registro_pagos));
                data.add(new item_menu("Registrar pedidos", R.drawable.pedidos));
            default:
                data.add(new item_menu("Ver inventario", R.drawable.inventario));
                break;
        }
        adapter = new MyAdapter(this, data,  (MyAdapter.OnItemClickListener) this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, data.size()>1? 2:1));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onItemClick(int position) {
        switch (data.get(position).getTexto()){
            case "Ver inventario":
                Intent intent = new Intent(getApplicationContext(), Inventory.class);
                startActivity(intent);
                break;
            case "Registro de pagos":
                Control.ToastExito(this, Control.title_toast_exito, data.get(position).getTexto());
                break;
            case "Registrar pedidos":
                Control.ToastExito(this, Control.title_toast_exito, data.get(position).getTexto());
                break;
            case "Historial inventario":
                Intent intent2 = new Intent(getApplicationContext(), InventoryHistory.class);
                startActivity(intent2);
                break;
            default:
                Control.ToastFallo(this, Control.title_toast_fallo, "Hubo un error");
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
                Control.Intent(Home.this, MainActivity.class);
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