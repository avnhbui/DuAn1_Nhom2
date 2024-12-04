package com.vanhbui04.duan1_nhom2.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.fragment.FragmentKhachHang;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;
import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> {
    FragmentKhachHang fragment;
    private Context context;
    private ArrayList<TaiKhoan> list,listOld;
    TaiKhoanDAO taikhoanDAO;
    private List<TaiKhoan> listSearch = new ArrayList<>();
    RecyclerView rcc;


    public KhachHangAdapter(Context context, ArrayList<TaiKhoan> list) {
        this.context = context;
        this.list = list;
        taikhoanDAO = new TaiKhoanDAO(context);
    }
    public KhachHangAdapter(@NonNull Context context, FragmentKhachHang fragmentKhachHang, List<TaiKhoan> listSearch) {
        this.context = context;
        this.fragment = fragmentKhachHang;
        this.listSearch = listSearch;
    }
    @NonNull
    @Override
    public KhachHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_khachhang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangAdapter.ViewHolder holder, int position) {
        holder.txtmatk.setText("Mã TK: " + list.get(position).getMaTk());
        holder.txttenDN.setText("Tên đăng nhập: " + list.get(position).getTenDN());
        holder.txtmk.setText("Mật khẩu: " + list.get(position).getMatKhau());
        holder.txthoten.setText("Họ và tên: " + list.get(position).getHoten());
        holder.txtsdt.setText("SDT: " + list.get(position).getSdt());
        holder.txtdiachi.setText("Địa chỉ: " + list.get(position).getDiachi());
        TaiKhoan tk = list.get(position);
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdate(tk);

            }
        });

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa không ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(taikhoanDAO.xoaKhachHang(tk.getMaTk())){
                            list.clear();
                            list.addAll(taikhoanDAO.getDSDL());
                            notifyDataSetChanged();

                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xoa that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    public void dialogUpdate(TaiKhoan tk){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view1 = inflater.inflate(R.layout.item_addkhachhang,null );

        builder.setView(view1);
        EditText edittenDN = view1.findViewById(R.id.edittenDN);
        EditText edtmk = view1.findViewById(R.id.editmk);
        EditText edthoten = view1.findViewById(R.id.editHoten);
        EditText edtdiachi = view1.findViewById(R.id.editdiachi);
        EditText edtsdt= view1.findViewById(R.id.editsdt);

        edittenDN.setText(tk.getTenDN());
        edtmk.setText(tk.getMatKhau());
        edthoten.setText(tk.getHoten());
        edtsdt.setText(tk.getSdt());
        edtdiachi.setText(tk.getDiachi());
        builder.setNegativeButton("Cap nhat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tk.setTenDN(edittenDN.getText().toString());
                tk.setMatKhau(edtmk.getText().toString());
                tk.setHoten(edthoten.getText().toString());
                tk.setSdt(edtsdt.getText().toString());
                tk.setDiachi(edtdiachi.getText().toString());


                TaiKhoan taiKhoan = new TaiKhoan();
                if (TextUtils.isEmpty(edittenDN.getText().toString()) || TextUtils.isEmpty(edthoten.getText().toString()) || TextUtils.isEmpty(edtsdt.getText().toString()) || TextUtils.isEmpty( edtdiachi.getText().toString()) || TextUtils.isEmpty(edtmk.getText().toString())){
                    Toast.makeText(context, "Khong duoc de trong thong tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (taikhoanDAO.updateKhachHang(tk)){
                        list.clear();
                        list.addAll(taikhoanDAO.getDSDL());
                        notifyDataSetChanged();

                        Toast.makeText(context, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "cap nhat that bai", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        builder.setPositiveButton("Huy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog  = builder.create();
        dialog.show();

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmatk,txtdiachi,txtsdt,txttenDN, txthoten,txtmk;
        ImageView btnUpdate, btndelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmatk = itemView.findViewById(R.id.txtmatk);
            txttenDN = itemView.findViewById(R.id.txttenDN);
            txtmk = itemView.findViewById(R.id.txtmk);
            txthoten = itemView.findViewById(R.id.txtHoten);
            txtsdt = itemView.findViewById(R.id.txtSdt);
            txtdiachi = itemView.findViewById(R.id.txtdiachi);
            btndelete = itemView.findViewById(R.id.btndelete);
            btnUpdate = itemView.findViewById(R.id.btnedit);
        }
    }
}
