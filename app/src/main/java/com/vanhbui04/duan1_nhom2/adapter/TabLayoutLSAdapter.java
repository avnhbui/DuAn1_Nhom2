package com.vanhbui04.duan1_nhom2.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vanhbui04.duan1_nhom2.fragment.lscustomer.FragmentCXN;
import com.vanhbui04.duan1_nhom2.fragment.lscustomer.FragmentChoHuy;
import com.vanhbui04.duan1_nhom2.fragment.lscustomer.FragmentDXN;
import com.vanhbui04.duan1_nhom2.fragment.lscustomer.FragmentDangGiao;
import com.vanhbui04.duan1_nhom2.fragment.lscustomer.FragmentHuy;
import com.vanhbui04.duan1_nhom2.fragment.lscustomer.FragmentThanhCong;

public class TabLayoutLSAdapter extends FragmentStateAdapter {
    public TabLayoutLSAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new FragmentCXN();
        } else if (position == 1) {
            return new FragmentDXN();
        } else if (position == 2) {
            return new FragmentDangGiao();
        } else if (position == 3) {
            return new FragmentThanhCong();
        } else if (position == 4) {
            return new FragmentChoHuy();
        } else
            return new FragmentHuy();
    }


    @Override
    public int getItemCount() {
        return 6;
    }
}
