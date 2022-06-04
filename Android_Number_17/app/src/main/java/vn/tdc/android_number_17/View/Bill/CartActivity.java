package vn.tdc.android_number_17.View.Bill;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRouter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import vn.tdc.android_number_17.Adapter.GioHangAdapter;
import vn.tdc.android_number_17.Adapter.SanPhamAdapter;
import vn.tdc.android_number_17.Model.SanPhamModels;
import vn.tdc.android_number_17.Presenter.GioHangPreSenter;
import vn.tdc.android_number_17.Presenter.GioHangView;
import vn.tdc.android_number_17.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class CartActivity extends AppCompatActivity implements GioHangView {
    private RecyclerView rcVBill;
    private GioHangAdapter sanPhamAdapter;
    private GioHangPreSenter gioHangPreSenter;
    private ArrayList<SanPhamModels> arrayList;
    private Button btnthanhtoan;
    private  String s[]={"Thanh toán khi nhận hàng","Thanh toán MOMO"};
    private  long tongtien = 0;
    private ProgressBar progressBar;
    private  String hoten="",diachi="",sdt="";
    private  Spinner spinner;
    private  int check =  0 ;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cart);
        InitWidget();
        Init();
    }

    private void Init() {
        arrayList = new ArrayList<>();
        gioHangPreSenter = new GioHangPreSenter(this);
        gioHangPreSenter.HandlegetDataGioHang();
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size()>0){
                    DiaLogThanhToan();
                }else{
                    Toast.makeText(CartActivity.this, "Sản phẩm không có trong giỏ hàng !", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }
            //chức năng xóa sp trong giỏ hàng
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                AlertDialog.Builder buidler = new AlertDialog.Builder(CartActivity.this);
                buidler.setMessage("Bạn có muôn xóa  sản phẩm "+arrayList.get(pos).getTensp());
                buidler.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sanPhamAdapter.notifyDataSetChanged();
                        gioHangPreSenter.HandlegetDataGioHang(arrayList.get(pos).getId());
                        arrayList.remove(pos);
                        check = 1;

                    }
                });
                buidler.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sanPhamAdapter.notifyDataSetChanged();
                    }
                });
                buidler.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rcVBill);
    }

    private void DiaLogThanhToan() {
        Dialog dialog = new Dialog(CartActivity.this);
        dialog.setContentView(R.layout.dialog_thanhtoan);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CustomInit(dialog);

    }
    //Dien thong tin đặt hàng
    private void CustomInit(Dialog dialog) {
        spinner = dialog.findViewById(R.id.spinerphguongthuc);
        EditText edithoten=dialog.findViewById(R.id.edithoten);
        EditText editdiachi=dialog.findViewById(R.id.editdiachi);
        EditText editsdt=dialog.findViewById(R.id.editsdt);
        TextView txttongtien= dialog.findViewById(R.id.txttongtien);
        Button btnxacnhan = dialog.findViewById(R.id.btnxacnhan);
        dialog.setCanceledOnTouchOutside(false);

        ArrayAdapter arrayAdapter = new ArrayAdapter(CartActivity.this, android.R.layout.simple_list_item_1,s);
        spinner.setAdapter(arrayAdapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        tongtien = 0 ;
        for(SanPhamModels sanPhamModels : arrayList){
            tongtien += sanPhamModels.getGiatien()*sanPhamModels.getSoluong();
        }
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        txttongtien.setText("Tổng Tiền : "+ NumberFormat.getInstance().format(tongtien));
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoten=edithoten.getText().toString().trim();
                diachi =editdiachi.getText().toString().trim();
                sdt = editsdt.getText().toString().trim();
                if(hoten.length()>0){
                    if(diachi.length()>0){
                        if(sdt.length()>0){
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Calendar calendar = Calendar.getInstance();
                            String ngaydat = simpleDateFormat.format(calendar.getTime());
                            String phuongthuc = spinner.getSelectedItem().toString();
                            switch (spinner.getSelectedItemPosition()){
                                case 0:
                                    gioHangPreSenter.HandleAddHoaDon(ngaydat,diachi,hoten,sdt,phuongthuc,tongtien,arrayList);
                                    dialog.cancel();break;
                                case 1:



                                    dialog.cancel();
                                    break;

                            }
                            progressBar.setVisibility(View.VISIBLE);

                        }else{
                            Toast.makeText(CartActivity.this, "Số điện thoại không để trống", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CartActivity.this, "Địa chỉ không để trống", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CartActivity.this, "Họ tên không để trống", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    private void InitWidget() {
        rcVBill = findViewById(R.id.rcvBill);
        btnthanhtoan = findViewById(R.id.btnthanhtoan);
        progressBar= findViewById(R.id.progressbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void OnSucess() {
        if(check == 0){
            Toast.makeText(CartActivity.this, "Đặt Hàng Thành Công!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(CartActivity.this, "Thao tác thành công!", Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.GONE);
        sanPhamAdapter.notifyDataSetChanged();


    }

    @Override
    public void OnFail() {
        Toast.makeText(CartActivity.this, "Đặt Hàng thất bại !", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void getDataSanPham(String id, String idsp,String tensp, Long giatien, String hinhanh, String loaisp, Long soluong, String nhasanxuat, Long type, String baohanh) {
        try{
            arrayList.add(new SanPhamModels(id,idsp,tensp,giatien,hinhanh,loaisp,soluong,nhasanxuat,type,baohanh));
            sanPhamAdapter = new GioHangAdapter(CartActivity.this,arrayList,1);
            rcVBill.setLayoutManager(new LinearLayoutManager(CartActivity.this));
            rcVBill.setAdapter(sanPhamAdapter);
        }catch (Exception e){

        }
        progressBar.setVisibility(View.GONE);

    }
    //Thanh toán momo






}
