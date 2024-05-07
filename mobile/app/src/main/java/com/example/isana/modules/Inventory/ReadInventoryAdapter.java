package com.example.isana.modules.Inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.isana.R;

import java.util.List;


public class ReadInventoryAdapter extends RecyclerView.Adapter<ReadInventoryAdapter.ViewHolder>{
    Context context;
    private final List<InventoryAnswer> mData;
    String egg;
    public ReadInventoryAdapter(List<InventoryAnswer> data, Context context) {
        this.mData = data;
        this.context = context;
    }
    @NonNull
    @Override
    public ReadInventoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadInventoryAdapter.ViewHolder holder, int position) {
        InventoryAnswer inventoryAnswer = mData.get(position);
        holder.tvDate.setText(inventoryAnswer.getDate_item());
        holder.tvPrice.setText((inventoryAnswer.getPrice_item()));
        holder.tvCantidad.setText((inventoryAnswer.current_stock_item()));
        egg = inventoryAnswer.getName_type();
        int imageResource = R.drawable.cursor_style;
        if ("JUMBO".equalsIgnoreCase(egg)) {
            imageResource = R.drawable.jumbo;
        }else if ("AAA".equalsIgnoreCase(egg)){
            imageResource = R.drawable.aaa;
        }
        else if ("AA".equalsIgnoreCase(egg)){
            imageResource = R.drawable.aa;
        }
        Glide.with(holder.img.getContext())
                .load(imageResource)
                .into(holder.img);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TextView tvPrice;
        private final TextView tvCantidad;

        private final ImageView img;
        public ViewHolder(View itemHistory) {
            super(itemHistory);
            tvDate=itemHistory.findViewById(R.id.tvDate);
            tvPrice=itemHistory.findViewById(R.id.tvPrice);
            tvCantidad=itemHistory.findViewById(R.id.tvCantidad);
            img = itemHistory.findViewById(R.id.imgEgg);

        }
    }
}
