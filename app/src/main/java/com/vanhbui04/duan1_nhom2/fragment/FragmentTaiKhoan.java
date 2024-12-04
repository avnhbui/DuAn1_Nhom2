package com.vanhbui04.duan1_nhom2.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;
import com.vanhbui04.duan1_nhom2.R;

public class FragmentTaiKhoan extends Fragment {
    private Context context;

    private TextView tvHoTenNguoiDung;
    private TextView tvPhoneNguoiDung;
    private TextView tvDiacChiNguoiDung, tvmk, tvuser;
    private ImageView btnChinhSuaThongTin;

    private TaiKhoanDAO dao;
    private TaiKhoan user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        // Khởi tạo đối tượng context
        context = getContext();

        // Khởi tạo SharedPreferences sau khi context đã được khởi tạo
        SharedPreferences preferences = context.getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        tvHoTenNguoiDung = v.findViewById(R.id.tvHoTenNguoiDung);
        tvPhoneNguoiDung = v.findViewById(R.id.tvPhoneNguoiDung);
        tvDiacChiNguoiDung = v.findViewById(R.id.tvDiacChiNguoiDung);
        tvmk = v.findViewById(R.id.tvmk);
        tvuser = v.findViewById(R.id.tvusernd);

        btnChinhSuaThongTin = v.findViewById(R.id.imvcs);

        dao = new TaiKhoanDAO(context);

        if (username.equals("admin")){
            user = dao.getAD(username);
        }else {
            user = dao.getID(username);
        }

        // Đặt thông tin người dùng lên giao diện
        tvHoTenNguoiDung.setText(user.getHoten());
        tvPhoneNguoiDung.setText(user.getSdt());
        tvDiacChiNguoiDung.setText(user.getDiachi());
        tvmk.setText(user.getMatKhau());
        tvuser.setText(user.getTenDN());
        String phone = tvPhoneNguoiDung.getText().toString();
        SharedPreferences preferen = requireContext().getSharedPreferences("PHONE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferen.edit();
        editor.putString("call",phone);
        editor.apply();
        Log.d("SharedPreferences", "Giá trị lưu vào: " + phone);
        btnChinhSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });

        return v;
    }
    private void showUpdateDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_update, null);

        TextView etUser = dialogView.findViewById(R.id.edtuser);
        EditText etHoTen = dialogView.findViewById(R.id.ethoten);
        EditText etPhone = dialogView.findViewById(R.id.etPhone);
        EditText etDiaChi = dialogView.findViewById(R.id.etDiaChi);
        EditText edmk = dialogView.findViewById(R.id.edtMk);

        // Đặt thông tin hiện tại lên dialog
        etUser.setText("User: "+ user.getTenDN());
        etHoTen.setText(user.getHoten());
        etPhone.setText(user.getSdt());
        etDiaChi.setText(user.getDiachi());
        edmk.setText(user.getMatKhau());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String hoTen = etHoTen.getText().toString().trim();
                        String phone = etPhone.getText().toString().trim();
                        String diaChi = etDiaChi.getText().toString().trim();
                        String mk = edmk.getText().toString().trim();

                        // Kiểm tra dữ liệu nhập vào
                        if (hoTen.isEmpty() || phone.isEmpty() || diaChi.isEmpty()) {
                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        user.setHoten(hoTen);
                        user.setSdt(phone);
                        user.setDiachi(diaChi);
                        user.setMatKhau(mk);
                        if (user.getTenDN().equals("admin")) {
                            dao.updateAD(user);
                        } else {
                            dao.update(user);
                        }

                        // Cập nhật thông tin trên giao diện
                        tvHoTenNguoiDung.setText(hoTen);
                        tvPhoneNguoiDung.setText(phone);
                        tvDiacChiNguoiDung.setText(diaChi);
                        tvmk.setText(mk);


                        Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}