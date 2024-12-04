package com.vanhbui04.duan1_nhom2.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vanhbui04.duan1_nhom2.fragment.lsadmin.FragmentCXNHoaDon;
import com.vanhbui04.duan1_nhom2.fragment.lsadmin.FragmentChoHuyHD;
import com.vanhbui04.duan1_nhom2.fragment.lsadmin.FragmentDXNHoaDon;
import com.vanhbui04.duan1_nhom2.fragment.lsadmin.FragmentDangGiaoHD;
import com.vanhbui04.duan1_nhom2.fragment.lsadmin.FragmentHuyHD;
import com.vanhbui04.duan1_nhom2.fragment.lsadmin.FragmentThanhCongHD;

public class TabLayoutHoaDonAdapter extends FragmentStateAdapter {
    public TabLayoutHoaDonAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentCXNHoaDon();
            case 1:
                return new FragmentDXNHoaDon();
            case 2:
                return new FragmentDangGiaoHD();
            case 3:
                return new FragmentThanhCongHD();
            case 4:
                return new FragmentChoHuyHD();
            case 5:
                return new FragmentHuyHD();
            default:
                return null;
        }
    }


    @Override
    public int getItemCount() {
        return 6;
    }
}
