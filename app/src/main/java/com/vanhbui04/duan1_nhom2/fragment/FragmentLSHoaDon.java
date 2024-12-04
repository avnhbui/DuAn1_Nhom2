package com.vanhbui04.duan1_nhom2.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.vanhbui04.duan1_nhom2.adapter.LSAdapter;
import com.vanhbui04.duan1_nhom2.adapter.TabLayoutLSAdapter;
import com.vanhbui04.duan1_nhom2.dao.HoaDonDAO;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.HoaDon;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;

public class FragmentLSHoaDon extends Fragment {
    RecyclerView rcvdt;
    HoaDonDAO chitietDAO;
    TaiKhoan taiKhoan;
    TaiKhoanDAO taikhoanDAO;
    LSAdapter adapter_lichsu;
    ArrayList<HoaDon> list = new ArrayList<>();

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    TabLayoutLSAdapter tabLayoutLSAdapter;
    TabLayoutMediator tabLayoutMediator;
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_l_s_hoa_don, container, false);
        //rcvdt = v.findViewById(R.id.rclls);
        taikhoanDAO = new TaiKhoanDAO(getContext());
//        loaddata();

        // setHasOptionsMenu(true); // Bật menu
        tabLayout = v.findViewById(R.id.tablayout);
        viewPager2 = v.findViewById(R.id.viewpage2);
        tabLayoutLSAdapter = new TabLayoutLSAdapter(getActivity());
        viewPager2.setAdapter(tabLayoutLSAdapter);


        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Chờ xác nhận ");
                        break;
                    case 1:
                        tab.setText("Đã xác nhận");
                        break;
                    case 2:
                        tab.setText("Đang giao");
                        break;
                    case 3:
                        tab.setText("Thành công");
                        break;
                    case 4:
                        tab.setText("Chờ Hủy");
                        break;
                    case 5:
                        tab.setText("Hủy");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
        return v;
    }
}