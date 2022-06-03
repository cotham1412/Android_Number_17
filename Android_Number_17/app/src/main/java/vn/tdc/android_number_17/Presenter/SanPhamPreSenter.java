package com.thanhnhanshop.thanhnhanshop.Presenter;

import com.thanhnhanshop.thanhnhanshop.Model.SanPhamModels;

import java.util.ArrayList;

public class SanPhamPreSenter implements  ISanPham{
    private SanPhamModels sanPhamModels;
    private  SanPhamView callback;
    public   SanPhamPreSenter(SanPhamView callback){
        this.callback=callback;
        sanPhamModels = new SanPhamModels(this);

    }
    public void HandlegetDataSanPham(){
        sanPhamModels.HandlegetDataSanPham();
    }


    @Override
    public void getDataSanPham(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong,
                               String nhasanxuat, Long type,String baohanh) {
       callback.getDataSanPham(id,tensp,giatien,hinhanh,loaisp,mota,soluong,nhasanxuat,type,baohanh);
    }

    @Override
    public void OnEmptyList() {
        callback.OnEmptyList();
    }
// truyen dữ liệu qua màn hình
    @Override
    public void getDataSanPhamNB(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String baohanh) {
        callback.getDataSanPhamNB(id,tensp,giatien,hinhanh,loaisp,mota,soluong,nhasanxuat,type,baohanh);
    }

    public void HandlegetDataSanPham(String loaisp,int type) {
        sanPhamModels.HandlegetDataSanPham(loaisp,type);
    }

    public void HandlegetDataSanPhamNB() {
        sanPhamModels.HandlegetDataSanPhamNoiBat();
    }
}
