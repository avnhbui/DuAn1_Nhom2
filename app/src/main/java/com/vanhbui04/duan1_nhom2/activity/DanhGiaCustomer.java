package com.vanhbui04.duan1_nhom2.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.vanhbui04.duan1_nhom2.R;
import com.vanhbui04.duan1_nhom2.dao.DanhGiaDAO;
import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.DanhGia;
import com.vanhbui04.duan1_nhom2.model.DienThoai;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;

public class DanhGiaCustomer extends AppCompatActivity {
    private ImageView imageProduct;
    private TextView textProductName;
    private RatingBar ratingBar;
    private EditText editComment;
    private Button buttonSubmit;
    DanhGiaDAO danhgiaDAO;
    TaiKhoanDAO taikhoanDAO;
    DienThoaiDAO dienthoaiDAO;
    long millis = System.currentTimeMillis();
    java.sql.Date date = new java.sql.Date(millis);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_gia_customer);
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        danhgiaDAO = new DanhGiaDAO(this);
        taikhoanDAO = new TaiKhoanDAO(this);
        dienthoaiDAO = new DienThoaiDAO(this);
        // Tham chiếu các phần tử trong bảng đánh giá
        imageProduct = findViewById(R.id.imageProduct);
        textProductName = findViewById(R.id.textProductName);
        ratingBar = findViewById(R.id.ratingBar);
        editComment = findViewById(R.id.editComment);
        buttonSubmit = findViewById(R.id.buttonSubmit);


        Intent intent = getIntent();
        int tenDT = intent.getIntExtra("productId", 0);

        DienThoai dienThoai = dienthoaiDAO.getID(String.valueOf(tenDT));
        textProductName.setText(dienThoai.getTenDT());

        byte[] anhData = dienthoaiDAO.getAnhByMaDT(dienThoai.getMaDT());
        if (anhData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhData, 0, anhData.length);
            imageProduct.setImageBitmap(bitmap);
        } else {
            imageProduct.setImageResource(R.drawable.iphone15);
        }


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
                String username = preferences.getString("username", "");

                TaiKhoan maKhachHang = taikhoanDAO.getID(username);
                float rating = ratingBar.getRating();
                String comment = editComment.getText().toString();

                Intent intent = getIntent();
                int tenDT = intent.getIntExtra("productId", 0);

                DanhGia danhGia = new DanhGia();
                danhGia.setMaDt(tenDT);
                danhGia.setMaTk(maKhachHang.getMaTk());
                danhGia.setDiem((int) rating);
                danhGia.setNhanxet(comment);
                danhGia.setThoigian((java.sql.Date.valueOf(String.valueOf(date))));
                if (comment.isEmpty() || rating == 0) {
                    Toast.makeText(DanhGiaCustomer.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                long insertedId = danhgiaDAO.insert(danhGia);

                if (insertedId != -1) {
                    Toast.makeText(DanhGiaCustomer.this, "Thêm đánh giá thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DanhGiaCustomer.this, TrangChu.class));

                } else {
                    Toast.makeText(DanhGiaCustomer.this, "Thêm đánh giá thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}