package com.vanhbui04.duan1_nhom2.fragment.lscustomer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.adapter.LSAdapter;
import com.vanhbui04.duan1_nhom2.dao.HoaDonDAO;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.HoaDon;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentChoHuy extends Fragment {
    private RecyclerView recyclerView;
    private LSAdapter adapter;
    private List<HoaDon> chiTietList;
    private HoaDonDAO daoHD;
    private TaiKhoanDAO taikhoanDAO;
    private ArrayList<HoaDon> listsetAdapter;
    private boolean isFragmentVisible = false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            updateAdapterData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            updateAdapterData();
        }
    }

    private void updateAdapterData() {
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");
        TaiKhoan taiKhoan = taikhoanDAO.getID(username);

        listsetAdapter.clear(); // Xóa danh sách cũ

        for (HoaDon x : chiTietList) {
            if (x.getTrangThai() == 4 && x.getMaTk()==(taiKhoan.getMaTk())) {
                listsetAdapter.add(x); // Thêm vào danh sách mới chỉ khi trạng thái là 0 và mã khách hàng khớp
            }
        }

        adapter.notifyDataSetChanged(); // Cập nhật adapter
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ls_customer, container, false);

        recyclerView = view.findViewById(R.id.rcllsct);
        taikhoanDAO = new TaiKhoanDAO(getContext());
        daoHD = new HoaDonDAO(getContext());

        chiTietList = daoHD.getAll();
        listsetAdapter = new ArrayList<>();

        adapter = new LSAdapter(getContext(), listsetAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
