package com.example.isana.menus;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.isana.R;
import com.example.isana.multiusos.Control;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<item_menu> mData;
    private Context context;
    private OnItemClickListener mListener;


    public MyAdapter(Context context, List<item_menu> data, OnItemClickListener listener) {
        this.mData = data;
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        item_menu item = mData.get(position);
        holder.tv_menu.setText(item.getTexto());
        Glide.with(holder.img_menu.getContext())
                .load(item.getImg())
                .into(holder.img_menu);
        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
    }


    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_menu;
        private ImageView img_menu;
        private ConstraintLayout button1;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_menu=itemView.findViewById(R.id.tv_menu);
            img_menu=itemView.findViewById(R.id.img_menu);
            button1=itemView.findViewById(R.id.button1);
        }
    }
}
