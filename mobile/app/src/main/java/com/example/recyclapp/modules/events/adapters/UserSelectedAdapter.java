package com.example.recyclapp.modules.events.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclapp.R;
import com.example.recyclapp.modules.main.data.User;

import java.util.List;

public class UserSelectedAdapter extends RecyclerView.Adapter<UserSelectedAdapter.ViewHolder> {

    private List<User> mData;
    private OnItemClickListener mListener;

    public UserSelectedAdapter(List<User> mData, OnItemClickListener mListener) {
        this.mData = mData;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public UserSelectedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_selected, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSelectedAdapter.ViewHolder holder, int position) {
        User user = mData.get(position);
        holder.text.setText(user.getCode());
        holder.image.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateData(List<User> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text_code);
            image = itemView.findViewById(R.id.img_quit);
        }
    }
}
