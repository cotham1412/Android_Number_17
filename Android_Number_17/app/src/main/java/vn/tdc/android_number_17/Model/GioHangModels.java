//package vn.tdc.android_number_17.Model;
//
//
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//
//public class GioHangModels {
//    private  String id;
//    private  String id_sanpham;
//    private  long soluong;
//    private IGioHang callback;
//    private FirebaseFirestore db;
//
//    public  GioHangModels(){
//
//    }
//    public GioHangModels(String id, String id_sanpham, long soluong) {
//        this.id = id;
//        this.id_sanpham = id_sanpham;
//        this.soluong = soluong;
//    }
//
//    public GioHangModels(String id_sanpham, long soluong) {
//        this.id_sanpham = id_sanpham;
//        this.soluong = soluong;
//    }
//
//    public  GioHangModels(IGioHang callback){
//        this.callback=callback;
//        db= FirebaseFirestore.getInstance();
//
//    }
//
//    public long getSoluong() {
//        return soluong;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getId_sanpham() {
//        return id_sanpham;
//    }
//
//}
