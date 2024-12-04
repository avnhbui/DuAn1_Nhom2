package com.vanhbui04.duan1_nhom2.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vanhbui04.duan1_nhom2.adapter.KhachHangAdapter;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentKhachHang extends Fragment {
    private SearchView searchView;
    private List<TaiKhoan> list = new ArrayList<>();
    private KhachHangAdapter adapter;
    private RecyclerView rcc;
    private TaiKhoanDAO taikhoanDAO;
    private FloatingActionButton floatAdd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sapxep, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khach_hang, container, false);
        rcc = view.findViewById(R.id.rcckhachhang);
        floatAdd = view.findViewById(R.id.fab);
        taikhoanDAO = new TaiKhoanDAO(getContext());
        ArrayList<TaiKhoan> list = taikhoanDAO.getDSDL();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcc.setLayoutManager(manager);
        KhachHangAdapter adapter = new KhachHangAdapter(getContext(),list);
        rcc.setAdapter(adapter);
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });
        return view;
    }
    private void showdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_addkhachhang,null);
        builder.setView(view);
        EditText edittenDN = view.findViewById(R.id.edittenDN);
        EditText edtmk = view.findViewById(R.id.editmk);
        EditText edthoten = view.findViewById(R.id.editHoten);
        EditText edtdiachi = view.findViewById(R.id.editdiachi);
        EditText edtsdt= view.findViewById(R.id.editsdt);
        builder.setNegativeButton("Them", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tenDN = edittenDN.getText().toString();
                String mk = edtmk.getText().toString();
                String hoten = edthoten.getText().toString();
                String sdt = edtsdt.getText().toString();
                String diachi = edtdiachi.getText().toString();

                TaiKhoan taiKhoan = new TaiKhoan();
                if (TextUtils.isEmpty(hoten) || TextUtils.isEmpty(mk) || TextUtils.isEmpty(tenDN) || TextUtils.isEmpty(diachi) || TextUtils.isEmpty(sdt)){
                    Toast.makeText(getActivity(), "Khong duoc de trong thong tin", Toast.LENGTH_SHORT).show();
                }else {
                    boolean check = taikhoanDAO.themThanhVien(tenDN,mk,sdt,hoten,diachi);
                    if (check){
                        Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                        ArrayList<TaiKhoan> list = taikhoanDAO.getDSDL();
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        rcc.setLayoutManager(manager);
                        KhachHangAdapter adapter = new KhachHangAdapter(getContext(),list);
                        rcc.setAdapter(adapter);
                    }else {
                        Toast.makeText(getContext(), "Them that bai", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        builder.setPositiveButton("Huy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    //Sap xep theo ten tang dan
    private void sortBooksByNameAscending() {
        Collections.sort(list, new Comparator<TaiKhoan>() {
            @Override
            public int compare(TaiKhoan taiKhoan, TaiKhoan t1) {
                return taiKhoan.getTenDN().toLowerCase().compareTo(t1.getTenDN().toLowerCase());
            }
        });
        adapter = new KhachHangAdapter(getContext(), (ArrayList<TaiKhoan>) list);
        adapter.notifyDataSetChanged();
    }
    //sap xep giam dan
    private void sortBooksByNameDescending() {
        Collections.sort(list, new Comparator<TaiKhoan>() {
            @Override
            public int compare(TaiKhoan taiKhoan, TaiKhoan t1) {
                return t1.getTenDN().toLowerCase().compareTo(taiKhoan.getTenDN().toLowerCase());
            }
        });
        adapter = new KhachHangAdapter(getContext(), (ArrayList<TaiKhoan>) list);
        adapter.notifyDataSetChanged();
    }
}