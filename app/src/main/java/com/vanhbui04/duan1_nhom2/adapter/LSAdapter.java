package com.vanhbui04.duan1_nhom2.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.activity.ChiTietLS;
import com.vanhbui04.duan1_nhom2.dao.ChiTietDAO;
import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.HoaDonDAO;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.HoaDon;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;
import com.vanhbui04.duan1_nhom2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LSAdapter extends RecyclerView.Adapter<LSAdapter.ViewHolder> {
    Context context;
    ArrayList<HoaDon> list;
    ChiTietDAO dao;
    HoaDonDAO hoadonDAO;
    DienThoaiDAO dienthoaiDAO;
    TaiKhoanDAO taikhoanDAO;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public LSAdapter(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
        this.dao = new ChiTietDAO(context);
        hoadonDAO = new HoaDonDAO(context);
        dienthoaiDAO = new DienThoaiDAO(context);
        taikhoanDAO = new TaiKhoanDAO(context);
    }

    @NonNull
    @Override
    public LSAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lich_su, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LSAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HoaDon hoaDon = list.get(position);


        TaiKhoan taiKhoan = taikhoanDAO.getIDma(String.valueOf(hoaDon.getMaTk()));

        holder.txtmadon.setText("Mã hóa đơn: " + String.valueOf(hoaDon.getMaHD()));
        holder.txtngay.setText("Ngày đặt: " + String.valueOf(sdf.format(hoaDon.getNgay())));
        holder.txtdienthoai.setText("Số điện thoại: " + "" + taiKhoan.getSdt());
        holder.txtmaKH.setText("Tên khách hàng: " + "" + taiKhoan.getHoten());
        holder.txttongTien.setText(String.format("Tổng Đơn: %,.0f VNĐ ", hoaDon.getTongTien()));
        holder.txttrangThai.setText("Trạng thái: " + hoaDon.getTrangThai());
        if (hoaDon.getTrangThai() == 0) {
            holder.txttrangThai.setText("Trạng thái: Chờ xác nhân");
        } else if (hoaDon.getTrangThai() == 1) {
            holder.txttrangThai.setText("Trạng thái: Đã xác nhân");
        } else if (hoaDon.getTrangThai() == 2) {
            holder.txttrangThai.setText("Trạng thái: Đang giao");
            holder.btnhuy.setVisibility(View.GONE);
        } else if (hoaDon.getTrangThai() == 3) {
            holder.txttrangThai.setText("Trạng thái: Giao hàng thành công");
            holder.btnhuy.setVisibility(View.GONE);
        } else if (hoaDon.getTrangThai() == 4) {
            holder.txttrangThai.setText("Trạng thái: Chờ hủy");
            holder.btnhuy.setVisibility(View.GONE);
        }if (hoaDon.getTrangThai() == 5) {
            holder.txttrangThai.setText("Trạng thái: Đã hủy");
            holder.btnhuy.setVisibility(View.GONE);
        }
        holder.txtdiaChi.setText("Địa chỉ: " + taiKhoan.getDiachi());
        holder.txtphuongthuc.setText("Phương thức: " + hoaDon.getPhuongthuc());

        holder.btnchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Bạn đã chọn hóa đơn:  " + hoaDon.getMaHD(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ChiTietLS.class);
                intent.putExtra("productId", hoaDon.getMaHD());
                // Truyền mã sản phẩm
                context.startActivity(intent);
            }
        });
        holder.btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                if (hoaDon.getTrangThai()!=0){
                    builder.setTitle("Hủy Đơn Hàng");
                    builder.setMessage("Hủy sản phẩm này cần Admin xác nhận vui lòng chờ");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (hoadonDAO.update(hoaDon.getMaHD(), 4) > 0) {
                                Toast.makeText(context, "Chờ Admin xác nhận", Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "Hủy thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    builder.setTitle("Hủy Đơn Hàng");
                    builder.setMessage("Bạn có chắc chắn muốn hủy sản phẩm này?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (hoadonDAO.update(hoaDon.getMaHD(), 5) > 0) {
                                Toast.makeText(context, "Hủy thành công", Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                notifyDataSetChanged(); // Cập nhật lại dữ liệu trên RecyclerView // Cập nhật lại dữ liệu trên RecyclerView
                            } else {
                                Toast.makeText(context, "Hủy thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmaKH, txttongTien, txtngay, txttrangThai, txtdiaChi, txtmadon, txtdienthoai, txtphuongthuc;
        Button btnchitiet;
        Button btnhuy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmadon = itemView.findViewById(R.id.txtmadon);
            txtmaKH = itemView.findViewById(R.id.txtmakh);
            txtngay = itemView.findViewById(R.id.txtngaydat);
            txttrangThai = itemView.findViewById(R.id.txttrangthai);
            txtdiaChi = itemView.findViewById(R.id.txtdiachi);
            txttongTien = itemView.findViewById(R.id.tongTien);
            txtdienthoai = itemView.findViewById(R.id.txtdienthoai);
            txtphuongthuc = itemView.findViewById(R.id.txtphuongthuc);
            btnchitiet = itemView.findViewById(R.id.btnchitiett);
            btnhuy = itemView.findViewById(R.id.btnxoa);
        }
    }
}
