package com.vanhbui04.duan1_nhom2.adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.dao.LoaiDTDAO;
import com.vanhbui04.duan1_nhom2.model.LoaiSeries;
import com.vanhbui04.duan1_nhom2.R;

import java.util.List;

public class QLSeriesAdapter extends RecyclerView.Adapter<QLSeriesAdapter.ViewHolder> {
    private Context context;
    private List<LoaiSeries> ListLoaiDT;

    private LoaiDTDAO dao;

    public QLSeriesAdapter(Context context, List<LoaiSeries> dataList) {
        this.context = context;
        this.ListLoaiDT = dataList;
        dao=new LoaiDTDAO(context);
    }
    @NonNull
    @Override
    public QLSeriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chon_loai, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLSeriesAdapter.ViewHolder holder, int position) {
        int index = position;
        String data = ListLoaiDT.get(position).getTenLoaiSeri();
        holder.textView.setText(data);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.imgloai.getLayoutParams();
        layoutParams.setMargins(20, 10, 10, 10);
        holder.imgloai.setLayoutParams(layoutParams);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSeries  series = ListLoaiDT.get(index);
                showEditDialog(series);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListLoaiDT.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imgloai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtqlloaiseri);
            imgloai = itemView.findViewById(R.id.imgqlloai);
        }
    }
    private void showEditDialog(LoaiSeries series) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_series);
        dialog.setCancelable(false);

        TextView titledialog = dialog.findViewById(R.id.tilte_dialog_Series);
        EditText edtTenSeries = dialog.findViewById(R.id.editSeriesName);
        edtTenSeries.setText(series.getTenLoaiSeri());
        AppCompatButton btnSubmit = dialog.findViewById(R.id.btnSumbit);

        titledialog.setText("Series Repair");
        btnSubmit.setText("Repair");


        btnSubmit.setOnClickListener(v -> {
            String tenSeries = edtTenSeries.getText().toString().trim();

            if (TextUtils.isEmpty(tenSeries)) {
                showToast("Vui lòng nhập tên Series");
            } else {
                series.setTenLoaiSeri(tenSeries);

                dao.update(series);
                notifyDataSetChanged();


                //     ListLoaiDT.add(series);

                showToast("Đã Upload Series");
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
