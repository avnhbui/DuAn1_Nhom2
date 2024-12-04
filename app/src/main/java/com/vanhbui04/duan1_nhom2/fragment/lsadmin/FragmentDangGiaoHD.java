package com.vanhbui04.duan1_nhom2.fragment.lsadmin;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.vanhbui04.duan1_nhom2.adapter.QLHoaDonAdapter;
import com.vanhbui04.duan1_nhom2.dao.ChiTietDAO;
import com.vanhbui04.duan1_nhom2.dao.HoaDonDAO;
import com.vanhbui04.duan1_nhom2.model.HoaDon;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentDangGiaoHD extends Fragment {
    private SearchView searchView;
    private ListView listView;
    private QLHoaDonAdapter adapter;
    private List<HoaDon> hoaDonList;
    List<HoaDon> listsetAdapter;
    ChiTietDAO daoCT;
    HoaDonDAO daoHD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q_l_hoa_don, container, false);

        listView = view.findViewById(R.id.lvQuanLyHoaDon);
        daoCT = new ChiTietDAO(getContext());
        daoHD = new HoaDonDAO(getContext());
        hoaDonList = new ArrayList<>();
        listsetAdapter = new ArrayList<>();
        hoaDonList = daoHD.getAll();

        adapter = new QLHoaDonAdapter(getContext(), listsetAdapter);
        listView.setAdapter(adapter);

        return view;
    }
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
        listsetAdapter.clear(); // Clear old list
        hoaDonList = daoHD.getAll();

        for (HoaDon x : hoaDonList) {
            if (x.getTrangThai() == 2) {
                listsetAdapter.add(x); // Add to the new list only when the status is 0 and the account ID matches
            }else {
                listsetAdapter.remove(x);
                // Remove the invoice from the list if the status is 4 and the account ID matches
            }
        }

        adapter.notifyDataSetChanged(); // Update the adapter
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
