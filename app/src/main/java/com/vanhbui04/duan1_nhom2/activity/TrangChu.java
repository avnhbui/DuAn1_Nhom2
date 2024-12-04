package com.vanhbui04.duan1_nhom2.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.vanhbui04.duan1_nhom2.R;
import com.vanhbui04.duan1_nhom2.account.ManHinhCho;
import com.vanhbui04.duan1_nhom2.fragment.FragmentBanChay;
import com.vanhbui04.duan1_nhom2.fragment.FragmentDSDT;
import com.vanhbui04.duan1_nhom2.fragment.FragmentDoanhThu;
import com.vanhbui04.duan1_nhom2.fragment.FragmentHoaDon;
import com.vanhbui04.duan1_nhom2.fragment.FragmentKhachHang;
import com.vanhbui04.duan1_nhom2.fragment.FragmentLSHoaDon;
import com.vanhbui04.duan1_nhom2.fragment.FragmentQuanLySP;
import com.vanhbui04.duan1_nhom2.fragment.FragmentSeries;
import com.vanhbui04.duan1_nhom2.fragment.FragmentTaiKhoan;
import com.vanhbui04.duan1_nhom2.fragment.FragmentTrangChu;

public class TrangChu extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        drawerLayout = findViewById(R.id.drawerlayout);
        NavigationView navigationView = findViewById(R.id.navigationview);

        // Hiển thị tên người dùng trong header
        View headerLayout = navigationView.getHeaderView(0);
        textView = headerLayout.findViewById(R.id.textViewEmail);
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String user = preferences.getString("username", "");
        textView.setText("" + user + "!");

        // Xác định vai trò người dùng và hiển thị fragment tương ứng
        Fragment defaultFragment;
        String title;
        Menu menu = navigationView.getMenu();
        if (user != null && user.equalsIgnoreCase("admin")) {
            menu.findItem(R.id.nav_quanly).setVisible(true);
            menu.findItem(R.id.nav_thongke).setVisible(true);
            menu.findItem(R.id.sub_canhan).setVisible(true);
            defaultFragment = new FragmentTrangChu();
            title = "Trang Chủ";
        } else {
            menu.findItem(R.id.sub_muahang).setVisible(true);
            menu.findItem(R.id.sub_canhan).setVisible(true);
            defaultFragment = new FragmentDSDT();
            title = "Danh sách điện thoại";
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout, defaultFragment)
                .commit();
        toolbar.setTitle(title);

        // Xử lý sự kiện khi chọn mục trong NavigationView
        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            String fragmentTitle = "";

            int itemId = item.getItemId();
            if (itemId == R.id.sub_tt) {
                fragment = new FragmentDSDT();
                fragmentTitle = "Danh sách điện thoại";
            } else if (itemId == R.id.sub_ls) {
                fragment = new FragmentLSHoaDon();
                fragmentTitle = "Lịch sử hóa đơn";
            } else if (itemId == R.id.sub_taikhoan) {
                fragment = new FragmentTaiKhoan();
                fragmentTitle = "Tài khoản";
            } else if (itemId == R.id.sub_giohang) {
                startActivity(new Intent(TrangChu.this, GioHangCustomer.class));
            } else if (itemId == R.id.sub_Logout) {
                showExitDialog();
            } else if (itemId == R.id.nav_Home) {
                fragment = new FragmentTrangChu();
                fragmentTitle = "Trang Chủ";
            } else if (itemId == R.id.nav_donhang) {
                fragment = new FragmentHoaDon();
                fragmentTitle = "Đơn hàng";
            } else if (itemId == R.id.nav_khachhang) {
                fragment = new FragmentKhachHang();
                fragmentTitle = "Khách Hàng";
            } else if (itemId == R.id.nav_dienthoai) {
                fragment = new FragmentQuanLySP();
                fragmentTitle = "Quản Lý Sản Phẩm";
            } else if (itemId == R.id.nav_loaiseries) {
                fragment = new FragmentSeries();
                fragmentTitle = "Loại Series";
            } else if (itemId == R.id.sub_Top) {
                fragment = new FragmentBanChay();
                fragmentTitle = "Top 10";
            } else if (itemId == R.id.sub_DoanhThu) {
                fragment = new FragmentDoanhThu();
                fragmentTitle = "Doanh thu";
            }
            else if (itemId == R.id.sub_hotro) {
                startActivity(new Intent(TrangChu.this, HoTroKhachHang.class));
                fragmentTitle = "Chăm sóc khách hàng";
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, fragment)
                        .commit();
                toolbar.setTitle(fragmentTitle);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có chắc muống đăng xuất?");
        builder.setPositiveButton("Đăng xuất", (dialog, which) -> {
            SharedPreferences.Editor editor = getSharedPreferences("USER_DATA", MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(TrangChu.this, ManHinhCho.class));
            finish();
        });
        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
        Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}