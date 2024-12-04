package com.vanhbui04.duan1_nhom2.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.R;
import com.vanhbui04.duan1_nhom2.adapter.ChiTietLSAdapter;
import com.vanhbui04.duan1_nhom2.dao.HoaDonDAO;
import com.vanhbui04.duan1_nhom2.model.ChiTiet;

import java.util.ArrayList;

public class ChiTietLS extends AppCompatActivity {
    ChiTietLSAdapter adapterChitietls;
    RecyclerView rclctls;
    ArrayList<ChiTiet> list = new ArrayList<>();
    HoaDonDAO hoadonDAO;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ls);
        rclctls = findViewById(R.id.rclctls);
        loaddata();
        Toolbar toolbar = findViewById(R.id.toolbarrr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    public void loaddata() {
        Intent intent = getIntent();
        int tenDT = intent.getIntExtra("productId", 0);
        hoadonDAO = new HoaDonDAO(this);
        list = (ArrayList<ChiTiet>) hoadonDAO.getChiTietByMaHoaDon(Integer.parseInt(String.valueOf(tenDT)));
        adapterChitietls = new ChiTietLSAdapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rclctls.setLayoutManager(layoutManager);
        rclctls.setAdapter(adapterChitietls);
    }
}