package com.vanhbui04.duan1_nhom2.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vanhbui04.duan1_nhom2.adapter.Top10Adapter;
import com.vanhbui04.duan1_nhom2.dao.ThongKeDAO;
import com.vanhbui04.duan1_nhom2.model.Top;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentBanChay extends Fragment {
    private List<Top> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private Top10Adapter adapterTop10;
    private ThongKeDAO thongkeDAO;
    private SearchView searchView;
    public FragmentBanChay() {
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
        View view = inflater.inflate(R.layout.fragment_ban_chay, container, false);
        thongkeDAO=new ThongKeDAO(getContext());
        list=thongkeDAO.getTop();
        recyclerView=view.findViewById(R.id.rcltop10);
        adapterTop10=new Top10Adapter(getContext(), (ArrayList<Top>) list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterTop10);
        return view;
    }

    @Override
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
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);    }
}