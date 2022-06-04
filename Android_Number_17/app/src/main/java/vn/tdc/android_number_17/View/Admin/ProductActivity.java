package com.thanhnhanshop.team3shop.View.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.thanhnhanshop.team3shop.Model.SanPhamModels;
import com.thanhnhanshop.team3shop.Presenter.ISanPham;
import com.thanhnhanshop.team3shop.R;
import com.thanhnhanshop.team3shop.dangsanphamActivity;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    ProductAdapter adapter;
    RecyclerView rcv;
    private SanPhamModels sanPhamModels;
    private ArrayList<SanPhamModels> arr_sp = new ArrayList<>();
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        rcv = findViewById(R.id.rcv);
        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        sanPhamModels = new SanPhamModels(new ISanPham() {
            @Override
            public void getDataSanPham(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String baohanh) {
                arr_sp.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, nhasanxuat, type, baohanh));
                adapter = new ProductAdapter(ProductActivity.this, arr_sp);
                rcv.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void OnEmptyList() {
                dialog.dismiss();
            }

            @Override
            public void getDataSanPhamNB(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String baohanh) {
                dialog.dismiss();
            }
        });
        dialog.show();
        sanPhamModels.HandlegetDataSanPhamAll();

        findViewById(R.id.image_add).setOnClickListener(view -> {
            startActivityForResult(new Intent(ProductActivity.this, dangsanphamActivity.class), 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                dialog.show();
                arr_sp.clear();
                sanPhamModels.HandlegetDataSanPhamAll();

            }
        }
    }
}
