package com.vanhbui04.duan1_nhom2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.vanhbui04.duan1_nhom2.R;

public class FragmentQuanLySP extends Fragment {
    private SearchView searchView;

    private final String[] tabTitles = {"Kinh Doanh","Ngừng Kinh Doanh","Danh Sách Đánh Gia"};
    public FragmentQuanLySP() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_s_p, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tablayoutqlsp);
        ViewPager2 viewPager2 = view.findViewById(R.id.vp2qlsp);
        adapter_tablayout adapter_tablayoutSp = new adapter_tablayout(getActivity());
        viewPager2.setAdapter(adapter_tablayoutSp);
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
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
class adapter_tablayout extends FragmentStateAdapter {
    public adapter_tablayout(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentKinhDoanh();
            case 1:
                return new FragmentNgungKinhDoanh();
            case 2:
                return new FragmentDanhGia();
            default:
                return null;
        }
    }


    @Override
    public int getItemCount() {
        return 3;
    }
}