package com.example.recyclapp.modules.products.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclapp.R;
import com.example.recyclapp.modules.products.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Item> mData;
    private OnItemClickListener mListener;

    public ProductAdapter(List<Item> mData, OnItemClickListener mListener) {
        this.mData = mData;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mData.get(position);
        holder.productName.setText(item.getName());
        switch (item.getColor().getName().toUpperCase()) {
            case "ROJO":
                holder.productImage.setImageResource(R.mipmap.bin_red);
                break;
            case "AZUL":
                holder.productImage.setImageResource(R.mipmap.bin_blue);
                break;
            case "VERDE":
                holder.productImage.setImageResource(R.mipmap.bin_green);
                break;
            case "GRIS":
                holder.productImage.setImageResource(R.mipmap.bin_gray);
                break;
            default:
                holder.productImage.setImageResource(R.mipmap.bin_black);
                break;
        }
        holder.layout.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateData(List<Item> newData) {
        mData = new ArrayList<>();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.img_product);
            productName = itemView.findViewById(R.id.text_product);
            layout = itemView.findViewById(R.id.item_product);
        }
    }
}
