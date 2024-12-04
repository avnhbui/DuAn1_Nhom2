package com.vanhbui04.duan1_nhom2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.vanhbui04.duan1_nhom2.adapter.TabLayoutHoaDonAdapter;
import com.vanhbui04.duan1_nhom2.R;

public class FragmentHoaDon extends Fragment {

    TabLayoutHoaDonAdapter adapter_tablayout_ls;
    private final String[] tabTitles = {"Chờ xác nhận", "Đã xác nhận", "Đang giao", "Thành công", "Chờ Hủy", "Hủy"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tablayoutqlHD);
        ViewPager2 viewPager2 = view.findViewById(R.id.vp2qlHD);
        adapter_tablayout_ls = new TabLayoutHoaDonAdapter(getActivity());
        viewPager2.setAdapter(adapter_tablayout_ls);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        });
        tabLayoutMediator.attach();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        return view;
    }
}