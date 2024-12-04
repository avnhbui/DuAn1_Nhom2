package com.vanhbui04.duan1_nhom2.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.R;
import com.vanhbui04.duan1_nhom2.adapter.GioHangAdapter;
import com.vanhbui04.duan1_nhom2.dao.ChiTietDAO;
import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.GioHangDAO;
import com.vanhbui04.duan1_nhom2.dao.HoaDonDAO;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.ChiTiet;
import com.vanhbui04.duan1_nhom2.model.GioHang;
import com.vanhbui04.duan1_nhom2.model.HoaDon;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;

import java.util.ArrayList;

public class GioHangCustomer extends AppCompatActivity {
    private RecyclerView rcvgh;
    private GioHangAdapter adapterGioHang;
    private TextView txtTongSoLuong, txtphiship, txtTongGia, txthoten, txtdiachi, txtsdt, dathang, stk, tnh;
    private String phuongThucVanChuyen;
    private HoaDonDAO hoadonDAO;
    private ChiTietDAO chitietDAO;
    private TaiKhoanDAO taikhoanDAO;
    private DienThoaiDAO dienthoaiDAO;
    private GioHangDAO ghDAO;

    private ArrayList<GioHang> gioHangList = new ArrayList<>();
    private TaiKhoan taiKhoan;
    private RadioGroup radioGroup;
    private Double shipperPrice = 0.0;
    private long millis = System.currentTimeMillis();
    private java.sql.Date date = new java.sql.Date(millis);
    private ImageView imggio, suatt;
    private Button btnstar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gio_hang_customer);
        initializeViews();
        setupToolbar();
        setupListeners();
        loadUserData();
        setupRecyclerView();
    }
    private void initializeViews() {
        rcvgh = findViewById(R.id.cartView);
        txtTongSoLuong = findViewById(R.id.totalFeeTxt);
        txtphiship = findViewById(R.id.deliveryTxt);
        txtTongGia = findViewById(R.id.totalTxt);
        txthoten = findViewById(R.id.txthoten);
        txtdiachi = findViewById(R.id.txtdiachi);
        txtsdt = findViewById(R.id.txtsdt);
        dathang = findViewById(R.id.txtdathang);
        radioGroup = findViewById(R.id.rdovc);
        imggio = findViewById(R.id.gio);
        btnstar = findViewById(R.id.chu);
        suatt = findViewById(R.id.imgsua);
        stk = findViewById(R.id.textView10);
        tnh = findViewById(R.id.textView6);

        hoadonDAO = new HoaDonDAO(this);
        chitietDAO = new ChiTietDAO(this);
        taikhoanDAO = new TaiKhoanDAO(this);
        dienthoaiDAO = new DienThoaiDAO(this);
        stk.setVisibility(View.GONE);
        tnh.setVisibility(View.GONE);

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void setupListeners() {
        btnstar.setOnClickListener(v -> startActivity(new Intent(this, TrangChu.class)));

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioshipcode) {
                phuongThucVanChuyen = "Ship cod";
                shipperPrice = 20000.0;
                txtphiship.setText(String.format("%,d VNĐ", 20000));
                updateTotalValues();
                stk.setVisibility(View.GONE);
                tnh.setVisibility(View.GONE);

            } else if (checkedId == R.id.radioonile) {
                phuongThucVanChuyen = "Thanh toán online";
                shipperPrice = 0.0;
                txtphiship.setText(String.format("%,d VNĐ", 0));
                updateTotalValues();
                stk.setVisibility(View.VISIBLE);
                tnh.setVisibility(View.VISIBLE);
            }
        });

        dathang.setOnClickListener(v -> {
            if (gioHangList.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống bạn không thể đặt hàng", Toast.LENGTH_SHORT).show();
            } else if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Vui lòng chọn phương thức giao hàng", Toast.LENGTH_SHORT).show();
            } else {
                showKhachHangInputDialog();
            }
        });

        suatt.setOnClickListener(v -> showEditUserInfoDialog());
    }

    private void showKhachHangInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đặt hàng");
        builder.setMessage("Bạn có chắc chắn muốn đặt hàng này?");
        builder.setPositiveButton("Yes", (dialog, which) -> placeOrder());
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void placeOrder() {
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        TaiKhoan maKhachHang = taikhoanDAO.getID(username);
        String phuongThuc = phuongThucVanChuyen;

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaTk(maKhachHang.getMaTk());
        hoaDon.setPhuongthuc(phuongThuc);
        hoaDon.setTongTien((double) adapterGioHang.getTotalPrice());
        hoaDon.setTrangThai(0);
        hoaDon.setNgay((java.sql.Date.valueOf(String.valueOf(date))));

        long result = hoadonDAO.insert(hoaDon);

        if (result > 0) {
            int maHoaDonMoiNhat = hoadonDAO.getMaHoaDonMoiNhat();

            for (GioHang gioHang : gioHangList) {
                int maHoaDon = maHoaDonMoiNhat;
                ChiTiet chiTietSanPham = new ChiTiet(0, maHoaDon, gioHang.getMadt(), gioHang.getSoLuong(), gioHang.getGia());
                long chiTietResult = chitietDAO.insert(chiTietSanPham);
                if (chiTietResult > 0) {
                    startActivity(new Intent(this, TrangChu.class));
                    Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    radioGroup.clearCheck();
                    int soLuong = chiTietSanPham.getSoluong();
                    dienthoaiDAO.updateSoLuong(gioHang.getMadt(), soLuong);
                    showNotification();
                }
            }
            ghDAO.deleteAllGioHang(taiKhoan.getMaTk());
            gioHangList.clear();
            adapterGioHang.notifyDataSetChanged();
            updateTotalValues();
        } else {
            Toast.makeText(this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUserData() {
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        taiKhoan = taikhoanDAO.getID(username);
        txthoten.setText(String.valueOf(taiKhoan.getHoten()));
        txtsdt.setText(String.valueOf(taiKhoan.getSdt()));
        txtdiachi.setText(String.valueOf(taiKhoan.getDiachi()));
    }

    private void setupRecyclerView() {
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        taiKhoan = taikhoanDAO.getID(username);
        ghDAO = new GioHangDAO(this);
        gioHangList = ghDAO.getAllByMaKhachHang(taiKhoan.getMaTk());
        adapterGioHang = new GioHangAdapter(this, this, gioHangList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvgh.setLayoutManager(layoutManager);
        rcvgh.setAdapter(adapterGioHang);
        if (gioHangList.isEmpty()) {
            imggio.setVisibility(View.VISIBLE);
            btnstar.setVisibility(View.VISIBLE);
        } else {
            imggio.setVisibility(View.GONE);
            btnstar.setVisibility(View.GONE);
        }
    }

    public void updateTotalValues() {
        int totalQuantity = 0;
        double totalPrice = 0;

        for (GioHang gioHang : gioHangList) {
            totalQuantity += gioHang.getSoLuong();
            totalPrice += gioHang.getSoLuong() * gioHang.getGia();
        }
        totalPrice += shipperPrice;
        txtTongSoLuong.setText(String.valueOf(totalQuantity));
        txtTongGia.setText(String.format("%,.0f VNĐ", totalPrice));
    }

    @SuppressLint({"MissingPermission", "NotificationPermission"})
    private void showNotification() {
        RemoteViews notificationBigLayout = new RemoteViews(getPackageName(), R.layout.notification);

        notificationBigLayout.setImageViewResource(R.id.notification_icon, R.drawable.baseline_circle_notifications_24);
        notificationBigLayout.setTextViewText(R.id.notification_title, "Đặt hàng thành công");
        notificationBigLayout.setTextViewText(R.id.notification_message, "Đơn hàng của bạn đã được đặt thành công");
        notificationBigLayout.setViewPadding(R.id.notification_icon, 0, 0, 0, 0);
        notificationBigLayout.setViewPadding(R.id.notification_title, 0, 0, 0, 0);
        notificationBigLayout.setViewPadding(R.id.notification_message, 0, 0, 0, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomBigContentView(notificationBigLayout)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void showEditUserInfoDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_suadchi, null);

        EditText etHoTen = dialogView.findViewById(R.id.ethitenu);
        EditText etPhone = dialogView.findViewById(R.id.etPhoneu);
        EditText etDiaChi = dialogView.findViewById(R.id.etDiaChiu);

        etHoTen.setText(taiKhoan.getHoten());
        etPhone.setText(taiKhoan.getSdt());
        etDiaChi.setText(taiKhoan.getDiachi());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("Cập nhật", (dialog, which) -> {
                    String hoTen = etHoTen.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    String diaChi = etDiaChi.getText().toString().trim();

                    if (hoTen.isEmpty() || phone.isEmpty() || diaChi.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    taiKhoan.setHoten(hoTen);
                    taiKhoan.setSdt(phone);
                    taiKhoan.setDiachi(diaChi);
                    taikhoanDAO.update(taiKhoan);

                    txthoten.setText(hoTen);
                    txtsdt.setText(phone);
                    txtdiachi.setText(diaChi);

                    Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}
