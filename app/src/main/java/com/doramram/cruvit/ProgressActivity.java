package com.doramram.cruvit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.doramram.cruvit.Adapters.ProgressAdapter;
import com.doramram.cruvit.DB.DataBaseHelper;
import com.doramram.cruvit.Objects.Donation;
import com.doramram.cruvit.Objects.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;


public class ProgressActivity extends AppCompatActivity {
    private static final String FIREBASE_URL = "https://cruvit-dc242.firebaseio.com/";

    private static final String TAG = "ProgressActivity";
    private static final String TABLE_E_DONATION = "e_donation";
    private static final String KEY_DONATION_PRODUCT_CODE = "product_code";

    DataBaseHelper helper = new DataBaseHelper(this);
    private RecyclerView recyclerView;
    private ProgressAdapter adapter;
    private List<ProgressRecyclerItem> listProgress;

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();

    static final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    static List<ProgressRecyclerItem> recyclerItems = new ArrayList<ProgressRecyclerItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        recyclerView = (RecyclerView) findViewById(R.id.progress_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final DataBaseHelper helper = new DataBaseHelper(this);
        List<Product> productList = helper.getAllProducts();
        listProgress = convertToRecyclerItems(productList);
//        getListProgressFromFirebase();

        adapter = new ProgressAdapter(listProgress, this);
        recyclerView.setAdapter(adapter);

    }


    public List<ProgressRecyclerItem> convertToRecyclerItems(List<Product> productList){

        List<ProgressRecyclerItem> productRecyclerItemList = new ArrayList<ProgressRecyclerItem>();

        for (Product product:productList){
            Log.v(TAG, "get donations of product id: " + product.get_id());

            int currAmount = 0;
            int id = product.get_id();
            String name = product.get_name();
            if(helper.isDataExist(TABLE_E_DONATION, KEY_DONATION_PRODUCT_CODE, String.valueOf(id))){
                currAmount = helper.getDonationAmount(id);
            }

            ProgressRecyclerItem item = new ProgressRecyclerItem(id, name, currAmount);
            productRecyclerItemList.add(item);
            recyclerItems.add(item);
        }

        return productRecyclerItemList;
    }


    public void getListProgressFromFirebase(){

        database.addListenerForSingleValueEvent(new ValueEventListener() {

            List<ProgressRecyclerItem> productRecyclerItemList = new ArrayList<ProgressRecyclerItem>();
            Map<Integer, Integer> donationsMap = new HashMap<Integer, Integer>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // TODO: Expected a List while deserializing, but got a class java.util.HashMap
                ArrayList<Donation> donations = dataSnapshot.child("donations").
                        getValue(new GenericTypeIndicator<ArrayList<Donation>>(){});

                // TODO: Change the way of insertion to map in this method
                for (Donation donation : donations){
                    int productId = donation.get_productId();
                    int productAmount = donation.get_amount();
                    if (donationsMap.containsKey(productId)){
                        donationsMap.put(productId, donationsMap.get(productId) + productAmount);
                    } else {
                        donationsMap.put(productId, productAmount);
                    }
                }

                Iterator it = donationsMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    int key = (int) pair.getKey();
                    int val = (int) pair.getValue();

                    ProgressRecyclerItem item = new ProgressRecyclerItem(key, "no name", val);
                    recyclerItems.add(item);
                    it.remove(); // avoids a ConcurrentModificationException
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Failed to load,", databaseError.toException());
            }

        });

    }
}
