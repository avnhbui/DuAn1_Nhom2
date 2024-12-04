package com.vanhbui04.duan1_nhom2.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.LoaiDTDAO;
import com.vanhbui04.duan1_nhom2.model.DienThoai;
import com.vanhbui04.duan1_nhom2.model.LoaiSeries;
import com.vanhbui04.duan1_nhom2.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QLSPAdapter extends RecyclerView.Adapter<QLSPAdapter.ViewHolder> {
    private Fragment fragment;
    public static ImageView anhDT;
    public static int REQUEST_CODE_ADD = 111;
    private Context context;
    private ArrayList<DienThoai> list,listGoc;
    DienThoai dienThoai;
    private DienThoaiDAO dao;
    private LoaiDTDAO daoo;
    private List<LoaiSeries> listLS;
    private SpinnerTypeAdapter spinnerTypeAdapter;

    public QLSPAdapter(Context context, ArrayList<DienThoai> list,Fragment fragment) {
        this.context = context;
        this.list = list;
        this.fragment = fragment;
        this.listGoc = list;
        dao = new DienThoaiDAO(context);
        daoo = new LoaiDTDAO(context);
        listLS = daoo.getAll();
    }

    @NonNull
    @Override
    public QLSPAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qldt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLSPAdapter.ViewHolder holder, int position) {
        dienThoai = list.get(position);
        LoaiSeries loaiSeries = daoo.getID(String.valueOf(dienThoai.getMaLoaiSeri()));

        holder.tenDt.setText(dienThoai.getTenDT());
        holder.loaiDt.setText(loaiSeries.getTenLoaiSeri());
        holder.giaDt.setText(String.format("Giá: %,.0f VNĐ", dienThoai.getGiaTien()));
        holder.soluong.setText("Số Lượng: "+dienThoai.getSoLuong());

        try {
            Bitmap bitmap;
            byte[] hinhanhDT = dienThoai.getAnhDT();

            bitmap = BitmapFactory.decodeByteArray(hinhanhDT, 0, hinhanhDT.length);

            holder.imgqldt.setImageBitmap(bitmap);
            holder.itemView.setOnClickListener(v -> showEditDialog(position, bitmap));
        } catch (Exception e) {
            e.printStackTrace();
            Drawable vectorDrawable = AppCompatResources.getDrawable(context, R.drawable.baseline_phone_iphone_24);

            Bitmap bitmap = Bitmap.createBitmap(
                    vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888
            );
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
            holder.imgqldt.setImageBitmap(bitmap);
            holder.itemView.setOnClickListener(v -> showEditDialog(position, bitmap));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenDt, giaDt, loaiDt, soluong;
        ImageView imgqldt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenDt = itemView.findViewById(R.id.txtqltendt);
            giaDt = itemView.findViewById(R.id.txtqlgia);
            loaiDt = itemView.findViewById(R.id.txtqlloai);
            soluong = itemView.findViewById(R.id.txtqlsl);
            imgqldt = itemView.findViewById(R.id.imgqldt);
        }
    }
    private void showEditDialog(int index, Bitmap bitmap) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_load_dt);
        dialog.setCancelable(false);
        dao = new DienThoaiDAO(context);
        dienThoai = list.get(index);

        TextView titledialog = dialog.findViewById(R.id.tilte_dialog_load);
        titledialog.setText("Repair Product");

        EditText edtTenSP = dialog.findViewById(R.id.editqlName);
        EditText edtGia = dialog.findViewById(R.id.editqlPrice);
        EditText edtMota = dialog.findViewById(R.id.editqlDescribe);
        EditText edtsl = dialog.findViewById(R.id.editsoluong);
        Spinner editqlSeries = dialog.findViewById(R.id.editqlSeries);
        Spinner spinner = dialog.findViewById(R.id.editqlTT);
        AppCompatButton btnSubmit = dialog.findViewById(R.id.btnSumbit);

        ArrayList<String> spinnerItems = new ArrayList<>();
        String[] spinnerData = context.getResources().getStringArray(R.array.spinner_data);
        spinnerItems.addAll(Arrays.asList(spinnerData));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(dienThoai.getTrangThai());

        anhDT= dialog.findViewById(R.id.anhDT);
        anhDT.setImageBitmap(bitmap);


        edtTenSP.setText(dienThoai.getTenDT());
        edtGia.setText(String.valueOf(dienThoai.getGiaTien()));
        edtMota.setText(dienThoai.getMoTa());
        edtsl.setText(String.valueOf(dienThoai.getSoLuong()));

        spinnerTypeAdapter = new SpinnerTypeAdapter(context, listLS);
        editqlSeries.setAdapter(spinnerTypeAdapter);
        editqlSeries.setSelection(getSpinnerPosition(dienThoai.getMaLoaiSeri()));

        anhDT.setOnClickListener(v -> {
            openImageChooser();
        });

        btnSubmit.setText("Repair");
        btnSubmit.setOnClickListener(v -> {
            String tenSP = edtTenSP.getText().toString().trim();
            String gia = edtGia.getText().toString().trim();
            String mota = edtMota.getText().toString().trim();
            String soluong = edtsl.getText().toString().trim();

            BitmapDrawable bitmapDrawable = (BitmapDrawable) anhDT.getDrawable();
            Bitmap bitmapEdit = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            try {
                bitmapEdit.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                // Các xử lý khác sau khi nén thành công

                byte [] bytesAnh = byteArray.toByteArray();
                boolean check = dienThoai.getTrangThai() == spinner.getSelectedItemPosition();
                int selectedSeriesPosition = editqlSeries.getSelectedItemPosition();

                if (TextUtils.isEmpty(tenSP) || TextUtils.isEmpty(gia) || TextUtils.isEmpty(mota) || TextUtils.isEmpty(soluong)) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    dienThoai.setTenDT(tenSP);
                    dienThoai.setGiaTien(Double.parseDouble(gia));
                    dienThoai.setMoTa(mota);
                    dienThoai.setSoLuong(Integer.parseInt(soluong));
                    dienThoai.setMaLoaiSeri(listLS.get(selectedSeriesPosition).getMaLoaiSeri());
                    dienThoai.setAnhDT(bytesAnh);
                    dienThoai.setTrangThai(spinner.getSelectedItemPosition());

                    dao.update(dienThoai, dienThoai.getMaDT());
                    if (check){
                        list.set(index, dienThoai);
                    }
                    else {
                        list.remove(index);
                    }
                    notifyItemChanged(index);
                    Toast.makeText(context, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }} catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Vui lòng Chọn ảnh", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }
    public void updateAdapterData(int index) {
        notifyItemChanged(index);
    }

    public void sortDescending() {
        Collections.sort(listGoc, new Comparator<DienThoai>() {
            @Override
            public int compare(DienThoai dt1, DienThoai dt2) {
                return Integer.compare(dt2.getSoLuong(), dt1.getSoLuong());
            }
        });
        notifyDataSetChanged();
    }
    public void sortAscending() {
        Collections.sort(listGoc, new Comparator<DienThoai>() {
            @Override
            public int compare(DienThoai dt1, DienThoai dt2) {
                return Integer.compare(dt1.getSoLuong(), dt2.getSoLuong());
            }
        });
        notifyDataSetChanged();
    }

    public void filter(String keyword) {
        ArrayList<DienThoai> filteredList = new ArrayList<>();
        for (DienThoai dt : listGoc) {
            if (dt.getTenDT().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(dt);
            }
        }
        list = filteredList;
        notifyDataSetChanged();
    }
    private int getSpinnerPosition(int maLoaiSeri) {
        for (int i = 0; i < listLS.size(); i++) {
            if (listLS.get(i).getMaLoaiSeri() == maLoaiSeri) {
                return i;
            }
        }
        return 0;
    }
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        fragment.startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), REQUEST_CODE_ADD);

    }
}
