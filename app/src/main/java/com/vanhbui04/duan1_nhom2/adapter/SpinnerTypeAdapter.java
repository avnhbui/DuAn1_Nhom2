package com.vanhbui04.duan1_nhom2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vanhbui04.duan1_nhom2.model.LoaiSeries;
import com.vanhbui04.duan1_nhom2.R;

import java.util.List;

public class SpinnerTypeAdapter extends BaseAdapter {
    private final Context context;
    private List<LoaiSeries> loaiSeries;

    public SpinnerTypeAdapter(Context context, List<LoaiSeries> productTypeList) {
        this.context = context;
        this.loaiSeries = productTypeList;
    }
    @Override
    public int getCount() {
        return loaiSeries.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiSeries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_chon_loai, viewGroup, false);
        }

        TextView txtTypeNameSpinner = view.findViewById(R.id.txtqlloaiseri);
        txtTypeNameSpinner.setText(loaiSeries.get(i).getTenLoaiSeri());
        return view;
    }
}
