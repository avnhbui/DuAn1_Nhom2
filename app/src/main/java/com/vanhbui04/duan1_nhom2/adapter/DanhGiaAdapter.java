package com.vanhbui04.duan1_nhom2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.DanhGia;
import com.vanhbui04.duan1_nhom2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DanhGia> listLoai;
    private TaiKhoanDAO tkDao;
    private DienThoaiDAO dtDao;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public DanhGiaAdapter(Context context, ArrayList<DanhGia> listLoai) {
        this.context = context;
        this.listLoai = listLoai;
        tkDao = new TaiKhoanDAO(context);
        dtDao = new DienThoaiDAO(context);
    }
    @NonNull
    @Override
    public DanhGiaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhgia, parent, false);
        return new DanhGiaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhGiaAdapter.ViewHolder holder, int position) {
        DanhGia danhGia = listLoai.get(position);
        holder.tvten.setText("" + tkDao.getIDma(String.valueOf(danhGia.getMaTk())).getHoten());
        holder.tvsp.setText("" + dtDao.getID(String.valueOf(danhGia.getMaDt())).getTenDT());
        holder.tvngay.setText("Ngày đặt: " + String.valueOf(sdf.format(danhGia.getThoigian())));
        holder.tvdiem.setRating(Float.parseFloat("" + danhGia.getDiem()));
        holder.tvnhanxet.setText(danhGia.getNhanxet());
        Bitmap bitmap;
        byte[] hinhanhDT = dtDao.getID(String.valueOf(danhGia.getMaDt())).getAnhDT();
        if (hinhanhDT != null && hinhanhDT.length > 0) {
            bitmap = BitmapFactory.decodeByteArray(hinhanhDT, 0, hinhanhDT.length);
            holder.imageProduct.setImageBitmap(bitmap);
        } else {
            holder.imageProduct.setImageResource(R.drawable.baseline_phone_iphone_24);
        }
    }

    @Override
    public int getItemCount() {
        return listLoai.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvten, tvsp, tvngay, tvnhanxet,textProductNamet;
        RatingBar tvdiem;
        ImageView imageProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvten = itemView.findViewById(R.id.txtusert);
            tvsp = itemView.findViewById(R.id.textProductNamet);
            tvngay = itemView.findViewById(R.id.textDateTimet);
            tvdiem = itemView.findViewById(R.id.ratingBart);
            tvnhanxet = itemView.findViewById(R.id.textdgt);
            imageProduct = itemView.findViewById(R.id.imageProduct);
        }
    }
}
