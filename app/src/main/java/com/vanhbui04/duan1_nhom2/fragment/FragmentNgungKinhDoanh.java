package com.vanhbui04.duan1_nhom2.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vanhbui04.duan1_nhom2.adapter.QLSPAdapter;
import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.LoaiDTDAO;
import com.vanhbui04.duan1_nhom2.model.DienThoai;
import com.vanhbui04.duan1_nhom2.model.LoaiSeries;
import com.vanhbui04.duan1_nhom2.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FragmentNgungKinhDoanh extends Fragment {
    RecyclerView rcvqldt;
    FloatingActionButton btnAddSp;
    QLSPAdapter adapter;
    ArrayList<DienThoai> list;
    ArrayList<DienThoai> listdlm;

    List<LoaiSeries> listLS;
    DienThoaiDAO dtDAO;
    LoaiDTDAO loaidao;
    ImageView imgHinhSP;


    @Override
    public void onResume() {
        super.onResume();
        updateAdapterData();

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
        dtDAO = new DienThoaiDAO(getContext());
        loaidao = new LoaiDTDAO(getContext());
        listLS = loaidao.getAll();
        list = dtDAO.getAllNKD(1);

        adapter = new QLSPAdapter(getContext(), list, this);
        rcvqldt.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvqldt.setLayoutManager(linearLayoutManager);
        return view;
    }
    private void updateAdapterData() {
        list.clear(); // Clear old list
        listdlm = dtDAO.getAllNKD(1);

        for (DienThoai x : listdlm) {
            if (x.getTrangThai() == 1) {
                list.add(x);
                adapter.notifyDataSetChanged();// Add to the new list only when the status is 0 and the account ID matches
            }
            if (x.getTrangThai() == 0) {
                list.remove(x);
                // Remove the invoice from the list if the status is 4 and the account ID matches
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void loaddata() {
        list = dtDAO.getAllKDD(1);
        adapter = new QLSPAdapter(getContext(), list, this);
        rcvqldt.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvqldt.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QLSPAdapter.REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                    if (imgHinhSP != null) {
                        imgHinhSP.setImageBitmap(bitmap);
                    }
                    if (QLSPAdapter.anhDT != null) {
                        QLSPAdapter.anhDT.setImageBitmap(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    showToast("Không thể đọc được hình ảnh");
                }
            } else {
                showToast("Không có hình ảnh được chọn");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.asc) {
            adapter.sortAscending();
            return true;
        } else if (id == R.id.desc) {
            adapter.sortDescending();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}