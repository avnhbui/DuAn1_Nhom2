package com.vanhbui04.duan1_nhom2.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.vanhbui04.duan1_nhom2.dao.ThongKeDAO;
import com.vanhbui04.duan1_nhom2.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class FragmentDoanhThu extends Fragment {
    private Button tuNgay, denNgay, btnDoanhThu;
    private EditText ed_tuNGay, ed_denNGay;
    private TextView tv_soTien;
    private ThongKeDAO thongKeDao;
    private int mYear, mMonth, mDay;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FragmentDoanhThu() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        tuNgay = view.findViewById(R.id.btnbd);
        denNgay = view.findViewById(R.id.btnkt);
        btnDoanhThu = view.findViewById(R.id.btndoanhthu);
        ed_tuNGay = view.findViewById(R.id.edit_batdau);
        ed_denNGay = view.findViewById(R.id.edit_kethuc);
        tv_soTien = view.findViewById(R.id.txttongdoanhthu);
        DatePickerDialog.OnDateSetListener mDataTuNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
                ed_tuNGay.setText(sdf.format(c.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener mDataDenNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
                ed_denNGay.setText(sdf.format(c.getTime()));
            }
        };
        tuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getContext(), 0, mDataTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        denNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getContext(), 0, mDataDenNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongKeDao = new ThongKeDAO(getContext());
                Log.e("Doan thu", "Doanh thu");
                String tu = ed_tuNGay.getText().toString();
                String den = ed_denNGay.getText().toString();
                double doanhThu = thongKeDao.getDoanhThu(tu, den); // Lấy số tiền doanh thu từ thongKeDao

                // Định dạng số tiền thành VND
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String doanhThuDaFormat = format.format(doanhThu);

                // Đặt giá trị đã định dạng vào TextView
                tv_soTien.setText(doanhThuDaFormat, TextView.BufferType.NORMAL);
                //  tv_soTien.setText( thongKeDao.getDoanhThu(tu, den), TextView.BufferType.valueOf(" %,.0f VNĐ"));
            }
        });
        return view;
    }
}