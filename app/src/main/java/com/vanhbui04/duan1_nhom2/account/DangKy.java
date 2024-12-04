package com.vanhbui04.duan1_nhom2.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;
import com.vanhbui04.duan1_nhom2.R;

public class DangKy extends AppCompatActivity {
    EditText edttendn, edtmk, edthoten, edtdiach, edtsdt;
    Button btndk;
    TaiKhoanDAO taikhoanDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);

        setContentView(R.layout.activity_dang_ky);
        edttendn = findViewById(R.id.editUsernamedk);
        edtmk = findViewById(R.id.editpassdk);
        edthoten = findViewById(R.id.edithotendk);
        edtdiach = findViewById(R.id.editdcdk);
        edtsdt = findViewById(R.id.editsdtdk);
        btndk = findViewById(R.id.btndangky);
        taikhoanDAO = new TaiKhoanDAO(this);

        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setTenDN(edttendn.getText().toString());
                taiKhoan.setMatKhau(edtmk.getText().toString());
                taiKhoan.setHoten(edthoten.getText().toString());
                taiKhoan.setDiachi(edtdiach.getText().toString());
                taiKhoan.setSdt(edtsdt.getText().toString());
                if (edtdiach.getText().toString().isEmpty() || edtdiach.getText().toString().isEmpty() ||
                        edtdiach.getText().toString().isEmpty() || edtdiach.getText().toString().isEmpty() ||
                        edtdiach.getText().toString().isEmpty()) {
                    Toast.makeText(DangKy.this, "Ko để trống ô nhập", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (taikhoanDAO.insert(taiKhoan) > 0) {
                    Toast.makeText(DangKy.this, "Bạn đã đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DangKy.this, DangNhap.class));
                } else {
                    Toast.makeText(DangKy.this, "Thông tin k hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}