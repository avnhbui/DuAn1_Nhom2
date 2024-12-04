package com.vanhbui04.duan1_nhom2.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.GioHangDAO;
import com.vanhbui04.duan1_nhom2.dao.LoaiDTDAO;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.activity.GioHangCustomer;
import com.vanhbui04.duan1_nhom2.model.DienThoai;
import com.vanhbui04.duan1_nhom2.model.GioHang;
import com.vanhbui04.duan1_nhom2.model.LoaiSeries;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    Context context;
    ArrayList<GioHang> list;
    GioHangDAO dao;
    DienThoaiDAO daoo;
    LoaiSeries loaiSeries;
    DienThoai dienThoai;
    LoaiDTDAO loaidtDAO;
    TaiKhoanDAO taikhoanDAO;

    private GioHangCustomer mActivity;
    public GioHangAdapter(GioHangCustomer activity, Context context, ArrayList<GioHang> list) {
        this.mActivity = activity;
        this.context = context;
        this.list = list;
        this.dao = new GioHangDAO(context);
        daoo = new DienThoaiDAO(context);
        loaidtDAO = new LoaiDTDAO(context);
        taikhoanDAO=new TaiKhoanDAO(context);
    }

    @NonNull
    @Override
    public GioHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giohang, parent, false);
        return new GioHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.ViewHolder holder, int position) {
        GioHang gioHang = list.get(position);

        dienThoai = daoo.getID(String.valueOf(gioHang.getMadt()));
        loaiSeries = loaidtDAO.getID(String.valueOf(gioHang.getMadt()));

        holder.tenDt.setText("" + dienThoai.getTenDT());
        holder.soluong.setText("" + gioHang.getSoLuong());
        holder.giaDt.setText(String.format("%,.0f VNĐ", gioHang.getGia()));
        holder.tongtien.setText(String.format("%,.0f VNĐ", gioHang.getSoLuong() * gioHang.getGia() + 20000) );
        holder.soluong.setText(String.valueOf(gioHang.getSoLuong()));

        //update giá từng sản phẩm
        updateTotalPrice(holder, gioHang);
        //update giá tổng đơn hàng
        updateTotalValues();

        byte[] anhData = daoo.getAnhByMaDT(dienThoai.getMaDT());
        if (anhData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhData, 0, anhData.length);
            holder.anh.setImageBitmap(bitmap);
        } else {
            holder.anh.setImageResource(R.drawable.iphone15);
        }
        holder.imgcong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = gioHang.getSoLuong();
                dienThoai = daoo.getID(String.valueOf(gioHang.getMadt()));
                if (soLuong < dienThoai.getSoLuong()) {
                    soLuong++;
                    gioHang.setSoLuong(soLuong);
                    holder.soluong.setText(String.valueOf(soLuong));
                    updateTotalPrice(holder, gioHang);
                    notifyDataSetChanged();
                    updateTotalValues(); // Cập nhật tổng số lượng và tổng giá trị
                } else {
                    Toast.makeText(context, "Số lượng sản phẩm vượt quá số lượng có sẵn trong kho", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.imgtru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = gioHang.getSoLuong();
                if (soLuong > 1) {
                    soLuong--;
                    gioHang.setSoLuong(soLuong);
                    holder.soluong.setText(String.valueOf(soLuong));
                    updateTotalPrice(holder, gioHang);
                    notifyDataSetChanged(); // Cập nhật lại giao diện sau khi thay đổi số lượng
                    updateTotalValues(); // Cập nhật tổng số lượng và tổng giá trị
                }
            }
        });
        holder.txtdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = context.getSharedPreferences("USER_DATA", MODE_PRIVATE);
                String username = preferences.getString("username", "");
                TaiKhoan taiKhoan = taikhoanDAO.getID(username);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dao.delete(gioHang.getMaTk(),gioHang.getMadt()) > 0) {
                            list.clear();
                            list.addAll(dao.getAllByMaKhachHang(taiKhoan.getMaTk()));
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            updateTotalValues(); // Cập nhật tổng số lượng và tổng giá trị
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Các phần code khác của AlertDialog nếu có

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenDt, giaDt, soluong, tongtien;
        ImageView txtdelete, imgtru, imgcong, anh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenDt = itemView.findViewById(R.id.titleTxt);
            giaDt = itemView.findViewById(R.id.feeEachItem);
            soluong = itemView.findViewById(R.id.numberItemTxt);
            tongtien = itemView.findViewById(R.id.totalEachItem);
            txtdelete = itemView.findViewById(R.id.imgdelete);
            imgtru = itemView.findViewById(R.id.minusCartBtn);
            imgcong = itemView.findViewById(R.id.plusCartBtn);
            anh = itemView.findViewById(R.id.picCart);
        }
    }
    private void updateTotalPrice(GioHangAdapter.ViewHolder holder, GioHang gioHang) {
        int tongTien = (int) (gioHang.getSoLuong() * gioHang.getGia());
        holder.tongtien.setText(String.format("%,d VNĐ",tongTien));
    }

    private void updateTotalValues() {
        mActivity.updateTotalValues();
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (GioHang gioHang : list) {
            totalPrice += gioHang.getGia() * gioHang.getSoLuong();
        }
        return totalPrice;
    }
}
