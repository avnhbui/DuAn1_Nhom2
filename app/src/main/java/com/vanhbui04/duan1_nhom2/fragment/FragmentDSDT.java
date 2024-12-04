package com.vanhbui04.duan1_nhom2.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.vanhbui04.duan1_nhom2.adapter.DienThoaiAdapter;
import com.vanhbui04.duan1_nhom2.adapter.LoaiDTAdapter;
import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.LoaiDTDAO;
import com.vanhbui04.duan1_nhom2.activity.GioHangCustomer;
import com.vanhbui04.duan1_nhom2.model.DienThoai;
import com.vanhbui04.duan1_nhom2.model.LoaiSeries;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentDSDT extends Fragment {
    private SearchView searchView;
    RecyclerView rcvdt, rcvLoai;
    DienThoaiDAO dtDAO;
    DienThoaiAdapter dienThoaiAdapter;
    ImageView txtload, txttang, txtgiam;
    ArrayList<DienThoai> list = new ArrayList<>();
    ArrayList<DienThoai> listcleak = new ArrayList<>();
    ImageSlider imageSlider;

    public FragmentDSDT() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_d_s_d_t, container, false);
        rcvdt = v.findViewById(R.id.rcdt);
        rcvLoai = v.findViewById(R.id.recyclerView);
        txtload = v.findViewById(R.id.imglist);
        txtgiam = v.findViewById(R.id.imglen);
        txttang = v.findViewById(R.id.imgxuong);
        imageSlider = v.findViewById(R.id.constraintLayout);




        txtload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dsdt();
            }
        });
        txtgiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sxgiam();
            }
        });
        txttang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sxtang();
            }
        });
        dsdt();
        loaddata();
        slide();
        return v;
    }
    private void sxgiam() {
        Comparator<DienThoai> comparator = new Comparator<DienThoai>() {
            @Override
            public int compare(DienThoai o1, DienThoai o2) {
                return Double.compare(o2.getGiaTien(), o1.getGiaTien());
            }
        };
        Collections.sort(list, comparator);
        dienThoaiAdapter.notifyDataSetChanged();
    }


    private void sxtang() {
        Comparator<DienThoai> comparator = new Comparator<DienThoai>() {
            @Override
            public int compare(DienThoai o1, DienThoai o2) {
                return Double.compare(o1.getGiaTien(), o2.getGiaTien());
            }
        };
        Collections.sort(list, comparator);
        dienThoaiAdapter.notifyDataSetChanged();

    }

    public void loaddata() {
        LoaiDTDAO loaiDAO = new LoaiDTDAO(getContext());
        ArrayList<LoaiSeries> listLoai = loaiDAO.getAll();
        LoaiDTAdapter adapterLoai = new LoaiDTAdapter(getContext(), listLoai);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvLoai.setLayoutManager(layoutManager);
        rcvLoai.setAdapter(adapterLoai);
        adapterLoai.setOnItemClickListener(new LoaiDTAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Lấy loại sản phẩm được chọn
                LoaiSeries loaiSeries = listLoai.get(position);

                // Lấy danh sách sản phẩm tương ứng với loại sản phẩm được chọn
                ArrayList<DienThoai> listSanPham = dtDAO.getDienThoaiByLoai(loaiSeries.getMaLoaiSeri());

                // Cập nhật danh sách sản phẩm vào RecyclerView chính
                dienThoaiAdapter.updateData(listSanPham);
            }
        });
    }

    public void dsdt() {
        dtDAO = new DienThoaiDAO(getContext());
        list = dtDAO.getAllKD();
        dienThoaiAdapter = new DienThoaiAdapter(getContext(), list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvdt.setLayoutManager(gridLayoutManager);
        rcvdt.setAdapter(dienThoaiAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shop_search, menu);
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
                listcleak = dtDAO.getAllKD();
                list.clear();
                for (DienThoai dienThoai : listcleak) {
                    String tenDT = dienThoai.getTenDT();
                    if (tenDT.toLowerCase().contains(newText.toLowerCase().toString())) {
                        list.add(dienThoai);
                    }
                }
                dienThoaiAdapter.notifyDataSetChanged();
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.shop) {
            Intent intent = new Intent(getActivity(), GioHangCustomer.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void slide() {
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.slide2, "Trải Nghiệm Sự Mượt Mà", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.slide_4, "Bầu Trời Công nghệ", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.slide_5, "Thiết Kế Sang Trọng", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(imageList);
    }
}