package com.vanhbui04.duan1_nhom2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.model.Top;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Top> list;

    public Top10Adapter(Context context, ArrayList<Top> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public Top10Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top10, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top10Adapter.ViewHolder holder, int position) {
        Top top = list.get(position);
        holder.ten.setText(top.tenDt);
        holder.sl.setText("" + top.soLuong);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ten, sl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.tvtdt);
            sl = itemView.findViewById(R.id.tvsl);
        }
    }
}
