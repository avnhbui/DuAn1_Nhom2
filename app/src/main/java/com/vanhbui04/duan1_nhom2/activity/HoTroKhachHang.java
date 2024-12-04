package com.vanhbui04.duan1_nhom2.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.vanhbui04.duan1_nhom2.R;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;

public class HoTroKhachHang extends AppCompatActivity {
    TextView txtcall;
    private Context context;
    private TaiKhoanDAO dao;
    private TaiKhoan user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ho_tro_khach_hang);
        txtcall = findViewById(R.id.txtcall);
        dao=new TaiKhoanDAO(this);
        TextView txtmap = findViewById(R.id.txtvitri);
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Thiết lập sự kiện click cho nút back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //    SharedPreferences preferences = getSharedPreferences("PHONE", MODE_PRIVATE);
        // String data = preferences.getString("call", "");
        String data=dao.getSDT();
        txtcall.setText(data);
        txtcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });

        txtmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HoTroKhachHang.this, Maps.class));
            }
        });

    }
    private void showdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận liên hệ");
        builder.setMessage("Bạn có chắc chắn muốn liên hệ với cửa hàng ?");
        builder.setNegativeButton("Gọi điện", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sdt = txtcall.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sdt));
                //goi activity goi dien
                startActivity(intent);
                //dang ky xin quyen su dung chuc nang goi dien
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
