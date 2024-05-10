package com.example.recyclapp.modules.InventoryHistory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InventoryHistoryAdapter2 extends RecyclerView.Adapter<InventoryHistoryAdapter2.ViewHolder>{
    private Context context;
    private final HashMap<String, List<InventoryHistoryAnswer>> mData;
    private InventoryHistory inventoryHistory;

    public InventoryHistoryAdapter2(Context context, Map<String, List<InventoryHistoryAnswer>> mData, InventoryHistory ih ) {
        this.context = context;
        this.mData = (HashMap<String, List<InventoryHistoryAnswer>>) mData;
        this.inventoryHistory = ih;
    }
    @NonNull
    @Override
    public InventoryHistoryAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_inventory_f, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryHistoryAdapter2.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        List<String> fechas = new ArrayList<>(mData.keySet());
        String fecha = fechas.get(position);

        holder.fecha.setText(fecha);
        List<InventoryHistoryAnswer> objetosConFecha = mData.get(fecha);
        InventoryHistoryAdapter inventoryHistoryAdapter = new InventoryHistoryAdapter(objetosConFecha, inventoryHistory);
        holder.rv.setAdapter(inventoryHistoryAdapter);
        holder.rv.setLayoutManager(new LinearLayoutManager(context));
        holder.rv.scrollToPosition(getItemCount() - 1);

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fecha;
        private final RecyclerView rv;
        public ViewHolder(View itemHistory) {
            super(itemHistory);
            fecha=itemHistory.findViewById(R.id.tv_ihf_f);
            rv=itemHistory.findViewById(R.id.rv_ihf_rv);
        }
    }
}
