package com.vanhbui04.duan1_nhom2.fragment;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vanhbui04.duan1_nhom2.adapter.QLSeriesAdapter;
import com.vanhbui04.duan1_nhom2.dao.LoaiDTDAO;
import com.vanhbui04.duan1_nhom2.model.LoaiSeries;
import com.vanhbui04.duan1_nhom2.R;

import java.util.List;

public class FragmentSeries extends Fragment {
    private RecyclerView recyclerView;
    private QLSeriesAdapter adapter;
    private List<LoaiSeries> dataList;
    FloatingActionButton btnadd;
    private LoaiDTDAO dao;
    public FragmentSeries() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_series, container, false);
        recyclerView = view.findViewById(R.id.rcvqldt);
        btnadd = view.findViewById(R.id.floatBtnAdd);
        dao = new LoaiDTDAO(getContext());
        dataList = dao.getAll();

        adapter = new QLSeriesAdapter(getContext(), dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        int space = getResources().getDimensionPixelSize(R.dimen.item_space);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = space;
                } else {
                    outRect.top = 0;
                }
            }
        });

        return view;
    }
    private void showAddDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_series);
        dialog.setCancelable(false);

        TextView titledialog = dialog.findViewById(R.id.tilte_dialog_Series);
        EditText edtTenSeries = dialog.findViewById(R.id.editSeriesName);
        AppCompatButton btnSubmit = dialog.findViewById(R.id.btnSumbit);

        titledialog.setText("Series Add");
        btnSubmit.setText("Add");


        btnSubmit.setOnClickListener(v -> {
            String tenSeries = edtTenSeries.getText().toString().trim();

            if (TextUtils.isEmpty(tenSeries)) {
                showToast("Vui lòng nhập tên sản phẩm");
            } else {

                LoaiSeries series = new LoaiSeries();
                series.setMaLoaiSeri(dataList.size()+1);
                series.setTenLoaiSeri(tenSeries);

                dao.add(series);
                dataList.add(series);
                adapter.notifyDataSetChanged();


                showToast("Đã Thêm Series");
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}