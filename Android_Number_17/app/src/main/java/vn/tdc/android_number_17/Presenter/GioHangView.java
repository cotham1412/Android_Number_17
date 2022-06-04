package vn.tdc.android_number_17.Presenter;

public interface GioHangView {
    void OnSucess();

    void OnFail();

    void getDataSanPham(String id, String idsp,String tensp, Long giatien, String hinhanh, String loaisp, Long soluong, String nhasanxuat, Long type, String baohanh);
}
