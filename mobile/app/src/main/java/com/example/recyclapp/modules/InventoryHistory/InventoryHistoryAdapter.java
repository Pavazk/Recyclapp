package com.example.recyclapp.modules.InventoryHistory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recyclapp.R;
import com.example.recyclapp.common.Utils;

import java.util.List;


public class InventoryHistoryAdapter extends RecyclerView.Adapter<InventoryHistoryAdapter.ViewHolder>{
    private final List<InventoryHistoryAnswer> mData;
    private InventoryHistory inventoryHistory;

    public InventoryHistoryAdapter(List<InventoryHistoryAnswer> mData, InventoryHistory ih) {
        this.mData = mData;
        this.inventoryHistory = ih;
    }

    @NonNull
    @Override
    public InventoryHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryHistoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        InventoryHistoryAnswer inventoryHistoryAnswer = mData.get(position);
        holder.typeMovement.setText(inventoryHistoryAnswer.getMovement_audit_inv());
        Glide.with(holder.buttonDetails.getContext())
                .load(R.drawable.eye_details)
                .into(holder.buttonDetails);
        Glide.with(holder.imgState.getContext())
                .load(R.drawable.green_circle)
                .into(holder.imgState);
        holder.buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    InventoryHistoryAnswer inventoryHistoryAnswer = mData.get(position);
                    String price="";
                    String amount="";
                    String howmade = "Hecho por: " + inventoryHistoryAnswer.getUser_audit_inv();
                    String type = "Tipo de huevo: " + inventoryHistoryAnswer.getName_type();
                    System.out.println(inventoryHistoryAnswer);
                    if(inventoryHistoryAnswer.getAdded_stock() ==null){
                        amount = "Cantidad: No modificado ";
                    }else{
                        if(inventoryHistoryAnswer.getAdded_stock().equalsIgnoreCase("0")){
                            amount = "Cantidad: No modificado ";
                        }else{
                            amount = "Cantidad editada: " + inventoryHistoryAnswer.getAdded_stock();
                        }
                    }

                    if (inventoryHistoryAnswer.getPrice_audit_inv() == null) {
                        if (inventoryHistoryAnswer.getNew_price_added() == null) {
                            price = "Precio: No modificado ";
                        }else{
                            if (inventoryHistoryAnswer.getNew_price_added().equals("0")) {
                                price = "Precio: No modificado ";
                            } else {
                                price = "Precio editado: " + inventoryHistoryAnswer.getNew_price_added();
                            }
                        }
                    }else{
                        if (inventoryHistoryAnswer.getPrice_audit_inv().equals("0")) {
                            price = "Precio: No modificado ";
                        } else {
                            price = "Precio editado: " + inventoryHistoryAnswer.getPrice_audit_inv();
                        }
                    }

                    DetailsModal(inventoryHistory, howmade, type, price, amount);
                }catch (Exception e){
                    Utils.ToastFallo(inventoryHistory, Utils.title_toast_fallo, "Error plataforma!");
                }

            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void DetailsModal(Activity activity, String made, String type, String price, String amount) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.details_modal, activity.findViewById(R.id.modal_window));
        TextView madeegg = layout.findViewById(R.id.tvmade);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView typeegg = layout.findViewById(R.id.tvtype);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView priceegg = layout.findViewById(R.id.tvpriceegg);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView amountegg = layout.findViewById(R.id.tvamount);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button accept = layout.findViewById(R.id.btn_accept_modal);
        madeegg.setText(made);
        typeegg.setText(type);
        priceegg.setText(price);
        amountegg.setText(amount);
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(layout);
        dialog.show();
        dialog.setCancelable(false);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView typeMovement;
        private final ImageView imgState;
        private final ImageButton buttonDetails;
        public ViewHolder(View itemHistory) {
            super(itemHistory);
            typeMovement=itemHistory.findViewById(R.id.typeMovement);
            imgState=itemHistory.findViewById(R.id.imgStateInventory);
            buttonDetails=itemHistory.findViewById(R.id.imgbDetails);
        }
    }
}
