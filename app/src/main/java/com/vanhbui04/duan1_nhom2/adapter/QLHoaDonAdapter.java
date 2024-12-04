package com.vanhbui04.duan1_nhom2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vanhbui04.duan1_nhom2.activity.ChiTietLS;
import com.vanhbui04.duan1_nhom2.dao.HoaDonDAO;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.HoaDon;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;
import com.vanhbui04.duan1_nhom2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class QLHoaDonAdapter extends ArrayAdapter<HoaDon> {

    private Context context;
    private List<HoaDon> donList, listGoc;
    private HoaDonDAO daoHD;
    TaiKhoanDAO daoTK;
    int trangThai;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public QLHoaDonAdapter(Context context, List<HoaDon> hoaDonList) {
        super(context, R.layout.item_ql_hoa_don, hoaDonList);
        this.context = context;
        this.donList = hoaDonList;
        this.listGoc = hoaDonList;
        daoHD = new HoaDonDAO(context);
        daoTK = new TaiKhoanDAO(context);
    }

    static class ViewHolder {
        TextView textViewMaHoaDon,textViewNgayHoaDon,textViewTongTien,textViewNguoiNhan,textViewDiaChiNhan,textViewSdt,textViewpt;
        Button btnxn, btnHuy;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ql_hoa_don, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewMaHoaDon = convertView.findViewById(R.id.maHoaDon);
            viewHolder.textViewTongTien = convertView.findViewById(R.id.hdTongTien);
            viewHolder.textViewNgayHoaDon = convertView.findViewById(R.id.ngayHoaDon);
            viewHolder.textViewNguoiNhan = convertView.findViewById(R.id.tvNguoiNhan);
            viewHolder.textViewDiaChiNhan = convertView.findViewById(R.id.tvDiachiNhan);
            viewHolder.textViewSdt = convertView.findViewById(R.id.tvsdt);
            viewHolder.textViewpt = convertView.findViewById(R.id.tvphuongthuc);

            viewHolder.btnxn = convertView.findViewById(R.id.btnXN);
            viewHolder.btnHuy = convertView.findViewById(R.id.btnHuyDon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HoaDon hoadonItem = donList.get(position);
        TaiKhoan taiKhoan = daoTK.getIDma(String.valueOf(hoadonItem.getMaTk()));

        viewHolder.textViewMaHoaDon.setText("Mã Hóa Đơn: " + hoadonItem.getMaHD());
        viewHolder.textViewNgayHoaDon.setText(sdf.format(hoadonItem.getNgay()));
        viewHolder.textViewTongTien.setText(String.format("Tổng Tiền: %,.0f VNĐ", hoadonItem.getTongTien()));//"Tổng Tiền: " + chiTietItem.getGiatien() + " VNĐ"
        viewHolder.textViewNguoiNhan.setText("Người Nhận: " + taiKhoan.getHoten());
        viewHolder.textViewDiaChiNhan.setText("Địa Chỉ: " + taiKhoan.getDiachi());
        viewHolder.textViewSdt.setText("Số điện thoại: " + taiKhoan.getSdt());
        viewHolder.textViewpt.setText("" + hoadonItem.getPhuongthuc());

        trangThai = hoadonItem.getTrangThai();
        if (trangThai == 0) {
            viewHolder.btnxn.setText("Xác Nhận");
        } else if (trangThai == 1) {
            viewHolder.btnxn.setText("Giao Hàng");
        } else if (trangThai == 2) {
            viewHolder.btnxn.setText("Hoàn Thành");
            viewHolder.btnHuy.setVisibility(View.GONE);
        } else if (trangThai == 3) {
            viewHolder.btnxn.setVisibility(View.GONE);
            viewHolder.btnHuy.setVisibility(View.GONE);
        } else if (trangThai == 4) {
            viewHolder.btnxn.setText("Xác Nhận");
            viewHolder.btnHuy.setVisibility(View.GONE);
        } else {
            viewHolder.btnxn.setVisibility(View.GONE);
            viewHolder.btnHuy.setVisibility(View.GONE);
        }

        viewHolder.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daoHD.update(hoadonItem.getMaHD(), 4);
                updateList();
            }
        });

        viewHolder.btnxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trangThai == 5) {
                    daoHD.update(hoadonItem.getMaHD(), 0);
                    updateList();
                } else {
                    daoHD.update(hoadonItem.getMaHD(), trangThai + 1);
                    updateList();
                }
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Bạn đã chọn hóa đơn: " + hoadonItem.getMaHD(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ChiTietLS.class);
                intent.putExtra("productId", hoadonItem.getMaHD()); // Truyền mã sản phẩm
                context.startActivity(intent);
            }
        });

        return convertView;
    }
    public void filter(String keyword) {
        ArrayList<HoaDon> filteredList = new ArrayList<>();
        for (HoaDon ct : listGoc) {
            String i = String.valueOf(ct.getMaHD());
            if (i.toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(ct);
            }
        }
        donList.clear();
        donList.addAll(filteredList);
        notifyDataSetChanged();
    }
    public void updateList() {
        donList.clear();
        for (HoaDon x: daoHD.getAll()) {
            HoaDon don = daoHD.getID(String.valueOf(x.getMaHD()));
            if (trangThai == don.getTrangThai()) {
                donList.add(x);
            }
        }
        notifyDataSetChanged();
    }
}
