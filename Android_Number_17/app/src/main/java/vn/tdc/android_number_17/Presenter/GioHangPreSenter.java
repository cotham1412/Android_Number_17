package vn.tdc.android_number_17.Presenter;

import vn.tdc.android_number_17.Model.GioHangModels;
import vn.tdc.android_number_17.Model.SanPhamModels;

import java.util.ArrayList;

public class GioHangPreSenter implements  IGioHang{
    private GioHangModels gioHangModels;
    private GioHangView callback;

    public GioHangPreSenter( GioHangView callback) {
        this.callback = callback;
        gioHangModels  = new GioHangModels(this);
    }
    public  void AddCart(String idsp){
        gioHangModels.AddCart(idsp);
    }
    public  void  HandlegetDataGioHang(){
        gioHangModels.HandlegetDataGioHang();
    }
    public  void  HandlegetDataGioHang(String id){
        gioHangModels.HandlegetDataGioHang(id);
    }

    @Override
    public void OnSucess() {
        callback.OnSucess();
    }

    @Override
    public void OnFail() {
        callback.OnFail();
    }

    @Override
    public void getDataSanPham(String id, String idsp,String tensp, Long giatien, String hinhanh, String loaisp, Long soluong, String nhasanxuat, Long type, String baohanh) {
        callback.getDataSanPham(id,idsp,tensp,giatien,hinhanh,loaisp,soluong,nhasanxuat,type,baohanh);
    }

    public void HandleAddHoaDon(String ngaydat, String diachi, String hoten, String sdt, String phuongthuc, long tongtien, ArrayList<SanPhamModels> arrayList) {
        gioHangModels.HandleThanhToan(ngaydat,diachi,hoten,sdt,phuongthuc,tongtien,arrayList);
    }

    public void HandlegetDataCTHD(String id) {
        gioHangModels.HandleGetDataCTHD(id);

    }
    public void HandlegetDataCTHD(String id,String uid) {
        gioHangModels.HandleGetDataCTHD(id,uid);

    }
}
