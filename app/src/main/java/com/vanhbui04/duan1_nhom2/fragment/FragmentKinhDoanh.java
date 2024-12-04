package com.vanhbui04.duan1_nhom2.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vanhbui04.duan1_nhom2.adapter.QLSPAdapter;
import com.vanhbui04.duan1_nhom2.adapter.SpinnerTypeAdapter;
import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.LoaiDTDAO;
import com.vanhbui04.duan1_nhom2.model.DienThoai;
import com.vanhbui04.duan1_nhom2.model.LoaiSeries;
import com.vanhbui04.duan1_nhom2.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentKinhDoanh extends Fragment {
    RecyclerView rcvqldt;
    FloatingActionButton btnAddSp;
    QLSPAdapter adapter;
    ArrayList<DienThoai> list;
    ArrayList<DienThoai> listdlm;

    List<LoaiSeries> listLS;
    DienThoaiDAO dtDAO;
    LoaiDTDAO loaidao;
    Dialog dialog;
    ImageView imgHinhSP;

    @Override
    public void onResume() {
        super.onResume();
        updateAdapterData();
//        int index = 0; // Chỉ số của mục cần cập nhật
//        adapter.updateAdapterData(index);
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
        btnAddSp.setOnClickListener(v -> showAddDialog());
        dtDAO = new DienThoaiDAO(getContext());
        loaidao = new LoaiDTDAO(getContext());
        listLS = loaidao.getAll();
        list = dtDAO.getAllKDD(0);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvqldt.setLayoutManager(linearLayoutManager);

        adapter = new QLSPAdapter(getContext(), list, this);
        rcvqldt.setAdapter(adapter);


        return view;
    }
    public void loaddata() {
        list = dtDAO.getAllKDD(0);
        adapter = new QLSPAdapter(getContext(), list, this);
        rcvqldt.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // Các dòng code khác (LinearLayoutManager và rcvqldt.setLayoutManager) không cần thiết để nằm trong phương thức này.
    }

    private void updateAdapterData() {
        list.clear(); // Clear old list
        listdlm = dtDAO.getAllKDD(0);

        for (DienThoai x : listdlm) {
            if (x.getTrangThai() == 0) {
                list.add(x);
                //  adapter.notifyDataSetChanged();// Add to the new list only when the status is 0 and the account ID matches
            }
            if (x.getTrangThai() == 1) {
                list.remove(x);
                // Remove the invoice from the list if the status is 4 and the account ID matches
            }
        }
        adapter.notifyDataSetChanged();

    }

    private void showAddDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_load_dt);
        dialog.setCancelable(false);
        dtDAO = new DienThoaiDAO(getContext());
        SpinnerTypeAdapter spinnerTypeAdapter;
        TextView titledialog = dialog.findViewById(R.id.tilte_dialog_load);
        EditText edtTenSP = dialog.findViewById(R.id.editqlName);
        Spinner spnLoaiSP = dialog.findViewById(R.id.editqlSeries);
        Spinner spinner = dialog.findViewById(R.id.editqlTT);

        imgHinhSP = dialog.findViewById(R.id.anhDT);
        EditText edtGia = dialog.findViewById(R.id.editqlPrice);
        EditText edtMoTa = dialog.findViewById(R.id.editqlDescribe);
        EditText edtSoLuong = dialog.findViewById(R.id.editsoluong);
        AppCompatButton btnAdd = dialog.findViewById(R.id.btnSumbit);

        imgHinhSP.setImageResource(R.drawable.baseline_phone_iphone_24);
        titledialog.setText("Add Product");

        spinnerTypeAdapter = new SpinnerTypeAdapter(getContext(), listLS);
        spnLoaiSP.setAdapter(spinnerTypeAdapter);

        ArrayList<String> spinnerItems = new ArrayList<>();
        ArrayList<String> spinnerData = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.spinner_data)));
        spinnerItems.addAll(spinnerData);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerItems);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        imgHinhSP.setOnClickListener(v -> openImageChooser());

        btnAdd.setText("Add");
        btnAdd.setOnClickListener(v -> {
            try {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinhSP.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] bytesAnh = byteArray.toByteArray();

                String tenSP = edtTenSP.getText().toString().trim();
                LoaiSeries loaiSP = (LoaiSeries) spnLoaiSP.getSelectedItem();
                DienThoai dienThoai = new DienThoai();
                if (TextUtils.isEmpty(tenSP) || loaiSP == null || TextUtils.isEmpty(edtGia.getText().toString())) {
                    showToast("Vui lòng nhập đầy đủ thông tin");
                } else {
                    try {
                        dienThoai.setMaLoaiSeri(loaiSP.getMaLoaiSeri());
                        dienThoai.setGiaTien(Double.parseDouble(edtGia.getText().toString().trim()));
                        dienThoai.setMoTa(edtMoTa.getText().toString().trim());
                        dienThoai.setAnhDT(bytesAnh);
                        dienThoai.setTenDT(tenSP);
                        dienThoai.setSoLuong(Integer.parseInt(edtSoLuong.getText().toString().trim()));
                        dienThoai.setTrangThai(spinner.getSelectedItemPosition());
                        dtDAO.add(dienThoai);
                        if (dienThoai.getTrangThai() == 0) {
                            list.add(dienThoai);
                        }
                        adapter.notifyDataSetChanged();
                        showToast("Thêm sản phẩm thành công");
                        dialog.dismiss();

                    } catch (NumberFormatException e) {
                        showToast("Lỗi định dạng số, vui lòng kiểm tra lại giá và số lượng");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("Vui lòng Chọn ảnh");
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), QLSPAdapter.REQUEST_CODE_ADD);
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
                    Toast.makeText(getContext(), "Không thể đọc được hình ảnh", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Không có hình ảnh được chọn", Toast.LENGTH_SHORT).show();
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