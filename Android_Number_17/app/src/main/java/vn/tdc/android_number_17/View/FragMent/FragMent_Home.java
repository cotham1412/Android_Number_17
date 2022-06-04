package vn.tdc.android_number_17.View.FragMent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import vn.tdc.android_number_17.Adapter.BannerAdapter;
import vn.tdc.android_number_17.Adapter.SanPhamAdapter;
import vn.tdc.android_number_17.Model.SanPhamModels;
import vn.tdc.android_number_17.Presenter.SanPhamPreSenter;
import vn.tdc.android_number_17.Presenter.SanPhamView;
import vn.tdc.android_number_17.R;

import java.util.ArrayList;

public class FragMent_Home  extends Fragment implements SanPhamView {
    View view;
    private ArrayList<String> arrayList;
    private ViewPager viewPager;
    private FirebaseFirestore db;
    private BannerAdapter bannerAdapter;
    private SanPhamPreSenter sanPhamPreSenter;
    private  ArrayList<SanPhamModels> arr_sp,arr_sp_nb;
    private SanPhamAdapter sanPhamAdapter,sanPhamNBAdapter;
    private RecyclerView rcvSP,rcvSpNoiBat;
    private ImageButton imgBtnDanhMuc;

    FragMent_HomeListener activityCallback;
    public interface FragMent_HomeListener {
        void onButtonClick();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            activityCallback = (FragMent_HomeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " You must implement FirstFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        InitWidget();
        Init();
        InitSanPham();

        imgBtnDanhMuc.setOnClickListener(view ->{
            activityCallback.onButtonClick();
        });

        return  view;
    }

    private void InitSanPham() {
        arr_sp = new ArrayList<>();
        arr_sp_nb = new ArrayList<>();
        sanPhamPreSenter = new SanPhamPreSenter(this);
        sanPhamPreSenter.HandlegetDataSanPham();
        sanPhamPreSenter.HandlegetDataSanPhamNB();
    }
    ///Tạo banner
    private void Init() {
        arrayList = new ArrayList<>();
        db= FirebaseFirestore.getInstance();
        db.collection("Banner").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                    arrayList.add(d.getString("hinhanh"));
                }
                bannerAdapter = new BannerAdapter(getContext(),arrayList);
                viewPager.setAdapter(bannerAdapter);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    //3s sang 1 banner khác
                    public void run() {
                        int k=viewPager.getCurrentItem();
                        if(k>=arrayList.size()-1){
                            k  = 0;
                        }else{
                            k++;
                        }
                        handler.postDelayed(this,3000);
                        viewPager.setCurrentItem(k,true);

                    }
                },3000);

            }
        });

    }

    private void InitWidget() {
        viewPager = view.findViewById(R.id.viewpager);
        rcvSP = view.findViewById(R.id.rcvSP);
        rcvSpNoiBat = view.findViewById(R.id.rcvNB);
        imgBtnDanhMuc = view .findViewById(R.id.home_danhmuc);
    }

    @Override
    public void getDataSanPham(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong,
                               String nhasanxuat, Long type,String baohanh) {
        arr_sp.add(new SanPhamModels(id,tensp,giatien,hinhanh,loaisp,mota,soluong,nhasanxuat,type,baohanh));
        sanPhamAdapter = new SanPhamAdapter(getContext(),arr_sp);
        rcvSP.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rcvSP.setAdapter(sanPhamAdapter);

    }

    @Override
    public void OnEmptyList() {

    }

    @Override
    public void getDataSanPhamNB(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String baohanh) {
        arr_sp_nb.add(new SanPhamModels(id,tensp,giatien,hinhanh,loaisp,mota,soluong,nhasanxuat,type,baohanh));
        sanPhamNBAdapter = new SanPhamAdapter(getContext(),arr_sp_nb,2);
        rcvSpNoiBat.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rcvSpNoiBat.setAdapter(sanPhamNBAdapter);
    }
}
