//package miteat.miteat.Model;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.firebase.client.ValueEventListener;
//
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Created by Itzik on 07/07/2016.
// */
//public class ModelFirebase {
//    Firebase myFirebaseRef;
//
//    ModelFirebase(Context context){
//        Firebase.setAndroidContext(context);
//        myFirebaseRef = new Firebase("https://luminous-fire-3504.firebaseio.com/");
//    }
//
////    public void getAllStudentsAsynch(final Model.GetStudentsListener listener) {
////        Firebase  stRef = myFirebaseRef.child("student");
////        // Attach an listener to read the data at our posts reference
////        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot snapshot) {
////                final List<Student> stList = new LinkedList<Student>();
////                Log.d("TAG", "There are " + snapshot.getChildrenCount() + " blog posts");
////                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
////                    Student st = stSnapshot.getValue(Student.class);
////                    Log.d("TAG", st.getFname() + " - " + st.getId());
////                    stList.add(st);
////                }
////                listener.onResult(stList);
////            }
////
////            @Override
////            public void onCancelled(FirebaseError firebaseError) {
////                System.out.println("The read failed: " + firebaseError.getMessage());
////                listener.onCancel();
////            }
////        });
////    }
////
////    public void getStudentById(String id, final Model.GetStudent listener) {
////        Firebase  stRef = myFirebaseRef.child("student").child(id);
////        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot snapshot) {
////                Student st = snapshot.getValue(Student.class);
////                Log.d("TAG", st.getFname() + " - " + st.getId());
////                listener.onResult(st);
////            }
////
////            @Override
////            public void onCancelled(FirebaseError firebaseError) {
////                System.out.println("The read failed: " + firebaseError.getMessage());
////                listener.onCancel();
////            }
////        });
////    }
////
////    public void add(Student st) {
////        Firebase  stRef = myFirebaseRef.child("student").child(st.getId());
////        stRef.setValue(st);
////    }
//}
