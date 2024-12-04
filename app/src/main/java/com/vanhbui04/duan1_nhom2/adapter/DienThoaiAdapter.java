package com.vanhbui04.duan1_nhom2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.activity.ChiTietDT;
import com.vanhbui04.duan1_nhom2.dao.DanhGiaDAO;
import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.LoaiDTDAO;
import com.vanhbui04.duan1_nhom2.model.DanhGia;
import com.vanhbui04.duan1_nhom2.model.DienThoai;
import com.vanhbui04.duan1_nhom2.model.LoaiSeries;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;

public class DienThoaiAdapter extends RecyclerView.Adapter<DienThoaiAdapter.ViewHolder>{
    Context context;
    DienThoaiDAO dao;
    LoaiDTDAO daoo;
    DanhGiaDAO dgDao;
    ArrayList<DienThoai> list;
    LoaiSeries loaiSeries;
    DienThoai dienThoai;

    public DienThoaiAdapter(Context context, ArrayList<DienThoai> list) {
        this.context = context;
        this.list = list;
        dao = new DienThoaiDAO(context);
        daoo = new LoaiDTDAO(context);
        dgDao = new DanhGiaDAO(context);
    }
    @NonNull
    @Override
    public DienThoaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dienthoai, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DienThoaiAdapter.ViewHolder holder, int position) {
        dienThoai = list.get(position);

        loaiSeries = daoo.getID(String.valueOf(dienThoai.getMaLoaiSeri()));

        holder.tenDt.setText("" + dienThoai.getTenDT());
        holder.loaiDt.setText(loaiSeries.getTenLoaiSeri());
        holder.giaDt.setText(String.format("%,.0f VNĐ", dienThoai.getGiaTien()));

        double tb = 0;
        int tong = 0, tg = 0;
        for (DanhGia x: dgDao.getAll()) {
            if (x.getMaDt() == dienThoai.getMaDT()){
                tong = tong+x.getDiem();
                tg = tg + 1;
            }
        }
        if (tong != 0 && tg != 0){
            tb = tong/tg;
        }
        holder.danhGiaTB.setText("Đánh Giá: " + tb);

        //lấy ảnh
        Bitmap bitmap;
        byte[] hinhanhDT = dienThoai.getAnhDT();
        if (hinhanhDT != null && hinhanhDT.length > 0) {
            bitmap = BitmapFactory.decodeByteArray(hinhanhDT, 0, hinhanhDT.length);
            holder.anhdt.setImageBitmap(bitmap);
        } else {
            holder.anhdt.setImageResource(R.drawable.baseline_phone_iphone_24);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    DienThoai dienThoai = list.get(position);

                    Intent intent = new Intent(context, ChiTietDT.class);
                    loaiSeries = daoo.getID(String.valueOf(dienThoai.getMaLoaiSeri()));
                    String maLoaiSeries = String.valueOf(loaiSeries.getTenLoaiSeri());
                    intent.putExtra("bitmapImage", hinhanhDT);
                    intent.putExtra("maDT", dienThoai.getMaDT());
                    intent.putExtra("tenDT", dienThoai.getTenDT());
                    intent.putExtra("maLoaiSeries", maLoaiSeries);
                    intent.putExtra("giaTien", dienThoai.getGiaTien());
                    intent.putExtra("moTa", dienThoai.getMoTa());
                    context.startActivity(intent);
                }
            }
        });
    }
    public void updateData(ArrayList<DienThoai> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenDt, giaDt, loaiDt, danhGiaTB;
        ImageView anhdt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenDt = itemView.findViewById(R.id.txttendt);
            giaDt = itemView.findViewById(R.id.txtgia);
            loaiDt = itemView.findViewById(R.id.txtloaisr);
            anhdt = itemView.findViewById(R.id.imganh);
            danhGiaTB = itemView.findViewById(R.id.danhGiaTB);
        }
    }
}
