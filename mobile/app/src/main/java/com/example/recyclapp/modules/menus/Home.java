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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recycler();
    }

    private void recycler() {
        recyclerView = findViewById(R.id.recyclerview);
        data = new ArrayList<>();
        data.add(new item_menu("CANECAS", R.drawable.inventario));
        data.add(new item_menu("PRODUCTOS", R.drawable.inventario));
        data.add(new item_menu("EVENTOS", R.drawable.inventario));
        data.add(new item_menu("PERSONAS", R.drawable.inventario));
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
            case "BINS":
                data.clear();
                data.add(new item_menu("CREATE_BINS", R.drawable.inventario));
                data.add(new item_menu("READ_BINS", R.drawable.inventario));
                data.add(new item_menu("UPDATE_BINS", R.drawable.inventario));
                data.add(new item_menu("DELETE_BINS", R.drawable.inventario));
                adapter.updateData(data);
                break;
            case "CREATE_BINS":
                break;
            case "READ_BINS":
                break;
            case "UPDATE_BINS":
                break;
            case "DELETE_BINS":
                break;
            case "PRODUCTS":
                data.clear();
                data.add(new item_menu("CREATE_PRODUCTS", R.drawable.inventario));
                data.add(new item_menu("READ_PRODUCTS", R.drawable.inventario));
                data.add(new item_menu("UPDATE_PRODUCTS", R.drawable.inventario));
                data.add(new item_menu("DELETE_PRODUCTS", R.drawable.inventario));
                adapter.updateData(data);
                break;
            case "CREATE_PRODUCTS":
                break;
            case "READ_PRODUCTS":
                break;
            case "UPDATE_PRODUCTS":
                break;
            case "DELETE_PRODUCTS":
                break;
            case "EVENTS":
                data.clear();
                data.add(new item_menu("CREATE_EVENTS", R.drawable.inventario));
                data.add(new item_menu("READ_EVENTS", R.drawable.inventario));
                data.add(new item_menu("UPDATE_EVENTS", R.drawable.inventario));
                data.add(new item_menu("DELETE_EVENTS", R.drawable.inventario));
                adapter.updateData(data);
                break;
            case "CREATE_EVENTS":
                break;
            case "READ_EVENTS":
                break;
            case "UPDATE_EVENTS":
                break;
            case "DELETE_EVENTS":
                break;
            case "PERSONS":
                data.clear();
                data.add(new item_menu("CREATE_PERSONS", R.drawable.inventario));
                data.add(new item_menu("READ_PERSONS", R.drawable.inventario));
                data.add(new item_menu("UPDATE_PERSONS", R.drawable.inventario));
                data.add(new item_menu("DELETE_PERSONS", R.drawable.inventario));
                adapter.updateData(data);
                break;
            case "CREATE_PERSONS":
                break;
            case "READ_PERSONS":
                break;
            case "UPDATE_PERSONS":
                break;
            case "DELETE_PERSONS":
                break;
            default:
                Utils.ToastFallo(this, Utils.title_toast_fallo, "Hubo un error");
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