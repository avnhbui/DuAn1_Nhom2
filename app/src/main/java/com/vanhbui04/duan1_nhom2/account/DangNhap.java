package com.vanhbui04.duan1_nhom2.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.R;
import com.vanhbui04.duan1_nhom2.activity.TrangChu;

public class DangNhap extends AppCompatActivity {
    long press;

    EditText edtuser, edtpass;
    Button btnsig;
    CheckBox checkBox;
    TaiKhoanDAO dao;
    TextView txtload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        loadData();

        edtuser = findViewById(R.id.editUsername);
        edtpass = findViewById(R.id.editPassword);
        btnsig = findViewById(R.id.btnLogin);
        checkBox = findViewById(R.id.chkb);
        txtload = findViewById(R.id.btnclick);
        dao = new TaiKhoanDAO(this);
        btnsig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edUsername = edtuser.getText().toString();
                String strPass = edtpass.getText().toString();
                if (edUsername.isEmpty() || strPass.isEmpty()) {
                    Toast.makeText(DangNhap.this, "Tên đăng nhập và pasword không để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (dao.checkLogin(edUsername, strPass) > 0 || edUsername.equals("admin") && strPass.equals("admin")) {
                        Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        checkRememberUser(edUsername, strPass, checkBox.isChecked());
                        Intent intent = new Intent(DangNhap.this, TrangChu.class);
                        startActivity(intent);
                        //  finish();
                        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username", edUsername); // Lưu tên đăng nhập vào SharedPreferences
                        editor.apply();
                    } else {
                        Toast.makeText(DangNhap.this, "Đăng nhập thất bại username hoặc password sai", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });



        txtload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhap.this, DangKy.class));
            }
        });
    }
    private void loadData() {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        Boolean kiemTra = pref.getBoolean("kt", false);
        checkBox = findViewById(R.id.chkb); // Khởi tạo checkBox
        edtuser = findViewById(R.id.editUsername);
        edtpass = findViewById(R.id.editPassword);

        if (checkBox != null) { // Kiểm tra xem checkBox đã được tìm thấy trong layout chưa
            checkBox.setChecked(kiemTra);
        }
        edtuser.setText(pref.getString("username", ""));
        edtpass.setText(pref.getString("password", ""));
    }


    private void checkRememberUser(String a, String b, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (status == true) {
            editor.putString("username", a);
            editor.putString("password", b);
            editor.putBoolean("kt", status);
        } else {
            editor.clear();
        }
        editor.commit();
    }


    @Override
    public void onBackPressed() {
        if (press + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(DangNhap.this, "nhấn lại để thoát", Toast.LENGTH_SHORT).show();
        }
        press = System.currentTimeMillis();
    }
}