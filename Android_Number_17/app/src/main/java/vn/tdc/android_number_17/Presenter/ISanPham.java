package com.thanhnhanshop.thanhnhanshop.Presenter;

public interface ISanPham {
    void getDataSanPham(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong,
                        String nhasanxuat, Long type,String baohanh);

    void OnEmptyList();

    void getDataSanPhamNB(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String baohanh);
}
