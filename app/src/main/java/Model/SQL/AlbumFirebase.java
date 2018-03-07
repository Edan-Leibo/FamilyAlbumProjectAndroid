package Model.SQL;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Model.Entities.Album.Album;
import Model.Firebase.ModelFirebase;

/**
 * Created by adima on 02/03/2018.
 */

public class AlbumFirebase {

    /*



    public static void getAllImagesAndObserve(String albumId,long lastUpdate,final ImageFirebase.Callback<List<Image>> callback){
        Log.d("TAG", "getAllImagesAndObserve " + lastUpdate);
        Log.d("TAG", "getAllImagessAndObserve");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("comments");
        DatabaseReference myRef = database.getReference("images").child(albumId);

        Query query = myRef.orderByChild("lastUpdated").startAt(lastUpdate);
        Log.d("TAG","the query is ok");

        ValueEventListener listener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("TAG","the data changed");

                List<Image> list = new LinkedList<Image>();
                if(dataSnapshot ==null){
                    Log.d("TAG","the snapshot is null");

                }
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Log.d("TAG","got the children");

                    Image image = snap.getValue(Image.class);


                    list.add(image);
                }
                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }


    public static void addImage(String albumId,Image image){
        Log.d("TAG", "add image to firebase");


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        String key = database.getReference("images").child(albumId).push().getKey();


        image.setImageId(key);

        HashMap<String, Object> json = image.toJson();
        json.put("lastUpdated", ServerValue.TIMESTAMP);


        //DatabaseReference myRef = database.getReference("albums");


       // Log.d("TAG","the command id is:"+comment.getCommentId());

        //DatabaseReference ref = database.getReference("albums").child(albumId).
        DatabaseReference ref = database.getReference("images").child(albumId).child(image.getImageId());

        ref.setValue(json);
        //myRef.child(employee.id).setValue(json);
    }

     */
    class MyDelete extends AsyncTask<Album,String,Boolean> {


        @Override
        protected Boolean doInBackground(Album... albums) {
            Log.d("TAG","starting delte from local storage in thread");
            if (albums!=null) {

                //3. update the local DB

                for (Album album : albums) {

                    Log.d("TAG","the name of the album is:"+album.getName());
                    Log.d("TAG","the id of the album is:"+album.getAlbumId());



                    AppLocalStore.db.albumDao().delete(album);

                }

            }
            return true;

        }
    }

    public void removeFromLocalDb(Album album){
        MyDelete delete= new MyDelete();
        delete.execute(album);

    }

    public interface Callback<T>{
        void onComplete(T data);
    }

    public static void listenToDel(DatabaseReference myRef) {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("TAG","the child removed**************");
                Album album = dataSnapshot.getValue(Album.class);
                Log.d("TAG","the name of the child is:"+album.getAlbumId());



            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void getAllAlbumsAndObserve(String serialNumber,final Callback<List<Album>> callback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("albums").child(serialNumber);


        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //List<Album> list = new LinkedList<Album>();

                List<Album> list = new LinkedList<Album>();



                for(DataSnapshot snap:dataSnapshot.getChildren()){

                    Album album = snap.getValue(Album.class);
                    Log.d("TAG",album.name);
                    list.add(album);
                }

                callback.onComplete(list);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG","error in db");

                callback.onComplete(null);

            }
        });
    }



    public static void getAllAlbumsAndObserve(String serialNumber,long lastUpdate,final Callback<List<Album>> callback){
        Log.d("TAG", "getAllAlbumsAndObserve " + lastUpdate);
        Log.d("TAG", "getAllAlbumsAndObserve");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("albums").child(serialNumber);

        Query query = myRef.orderByChild("lastUpdated").startAt(lastUpdate);
        Log.d("TAG","the query is ok");

        //query.removeEventListener();

        //listenToDel(myRef);

        ValueEventListener listener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("TAG","the data changed");

                List<Album> list = new LinkedList<Album>();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Log.d("TAG","got the children");
                    Log.d("TAG","got the children"+snap.toString());


                    Album album = snap.getValue(Album.class);
                        Log.d("TAG","got the data in Album repository"+album.name);
                        Log.d("TAG","got the data in Album repository"+album.location);
                        Log.d("TAG","got the data in Album repository"+album.serialNumber);

                        Log.d("TAG","got the data in Album repository"+album.date);

                    list.add(album);
                }
                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }


    public interface OnCreationAlbum{
        public void onCompletion(boolean success);
    }

    public static void addAlbum(Album album, String serialNumber, final OnCreationAlbum listener){

        Log.d("TAG", "add album to firebase");


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        String key = database.getReference("albums").child(serialNumber).push().getKey();



        album.albumId = key;

        HashMap<String, Object> json = album.toJson();
        json.put("lastUpdated", ServerValue.TIMESTAMP);

        //DatabaseReference myRef = database.getReference("albums");

        DatabaseReference ref = database.getReference("albums").child(serialNumber).child(album.albumId);
        ref.setValue(json, new DatabaseReference.CompletionListener() {


            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("TAG", "Error: Album could not be saved "
                            + databaseError.getMessage());
                    listener.onCompletion(false);
                } else {
                    listener.onCompletion(true);
                    Log.e("TAG", "Success : Album saved successfully.");

                }
            }
        });
        //ref.setValue(json);
        //myRef.child(employee.id).setValue(json);
    }

    public interface onRemove{
        public void onCompletion(boolean success);
    }

    public static void removeAlbum(Album album, final ModelFirebase.OnRemove listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("albums").child(album.getSerialNumber()).child(album.getAlbumId());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    snap.getRef().removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError!=null){
                                listener.onCompletion(false);
                            }
                            else{
                                listener.onCompletion(true);
                            }

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });


    }




}