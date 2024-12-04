package com.vanhbui04.duan1_nhom2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vanhbui04.duan1_nhom2.adapter.DanhGiaAdapter;
import com.vanhbui04.duan1_nhom2.dao.DanhGiaDAO;
import com.vanhbui04.duan1_nhom2.model.DanhGia;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;

public class FragmentDanhGia extends Fragment {
    RecyclerView rcvqldt;
    DanhGiaAdapter adapter;
    ArrayList<DanhGia> list;
    DanhGiaDAO danhgiaDAO;

    FloatingActionButton btnAddSp;
    @Override
    public void onResume() {
        super.onResume();

        loaddata();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kinh_doanh, container, false);

        rcvqldt = view.findViewById(R.id.rcvqldt);
        btnAddSp = view.findViewById(R.id.floatBtnAdd);
        btnAddSp.setVisibility(View.GONE);
        danhgiaDAO = new DanhGiaDAO(getContext());

        list = danhgiaDAO.getAll();
        adapter = new DanhGiaAdapter(getContext(), list);
        rcvqldt.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvqldt.setLayoutManager(linearLayoutManager);
        return view;
    }
    public void loaddata() {
        list = danhgiaDAO.getAll();
        adapter = new DanhGiaAdapter(getContext(), list);
        rcvqldt.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvqldt.setLayoutManager(linearLayoutManager);
    }
}