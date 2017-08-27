package com.doramram.cruvit;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

//import com.firebase.client.ValueEventListener;
import com.doramram.cruvit.Utils.Util;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doramram.cruvit.Adapters.ProductAdapter;
import com.doramram.cruvit.DB.DataBaseHelper;
import com.doramram.cruvit.Objects.Donation;
import com.doramram.cruvit.Objects.GlobalExternalId;
import com.doramram.cruvit.Objects.Product;
import com.doramram.cruvit.Objects.User;

public class ChooseProductActivity extends AppCompatActivity {

    private static final String TAG = "ChooseProductActivity";

    private static final String TABLE_E_USER = "e_user";
    private static final String TABLE_E_DONATOR = "e_donator";
    private static final String TABLE_E_DONATION = "e_donation";
    private static final String KEY_DONATION_PRODUCT_CODE = "product_code";
    private static final String KEY_DONATION_PRODUCT_AMOUNT = "product_amount";
    private static final String KEY_USER_FIREBASE_ID = "user_firebase_id";
    private static final String KEY_USER_PHONE = "user_phone";


    private Util util;
//    DataBaseHelper helper = new DataBaseHelper(this);
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductRecyclerItem> listItems;
    private View hiddenPanel;
    private boolean isPanelShown = false;
    public Map<Integer, Integer> donationsMap = new HashMap<Integer, Integer>();
    private String externalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product);

        externalId = ((GlobalExternalId) this.getApplication()).getExternalId();

        util = new Util(this, externalId, donationsMap, TAG);

        hiddenPanel = findViewById(R.id.hidden_panel);
        hiddenPanel.setVisibility(View.INVISIBLE);
        isPanelShown = false;

        recyclerView = (RecyclerView) findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        final DataBaseHelper helper = new DataBaseHelper(this);
        List<Product> productList = util.getHelper().getAllProducts();
        listItems = convertToRecyclerItems(productList);
        adapter = new ProductAdapter(listItems, this);
        recyclerView.setAdapter(adapter);

        // final submit button
        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            if(util.gotDonations()){

                hideHiddenLayout();

                if(util.needMoreUserDetails(externalId)){
                    util.displayAdditionalDetailsView();
                } else {
                    util.updateDonationsDb();
                    util.finishDonation();
                }

            }
            }
        });
    }


    public List<ProductRecyclerItem> convertToRecyclerItems(List<Product> productList){

        List<ProductRecyclerItem> productRecyclerItemList = new ArrayList<ProductRecyclerItem>();

        for (Product product:productList) {
            int id = product.get_id();
            String title = product.get_name();
            String description = product.get_description();
            int image_uri = product.get_image();

            ProductRecyclerItem item = new ProductRecyclerItem(id, title, description, image_uri);
            productRecyclerItemList.add(item);
        }

        return productRecyclerItemList;
    }


    public void updateDonationsMap(int productId, int productAmount){
//        donationsMap.put(productId, productAmount);
        util.updateDonationsMap(productId, productAmount);
        displayHiddenLayout();
    }


    public void hideHiddenLayout(){
        Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        hiddenPanel.startAnimation(bottomDown);
        hiddenPanel.setVisibility(View.INVISIBLE);
        isPanelShown = false;
    }


    public void displayHiddenLayout(){
        Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
        hiddenPanel.startAnimation(bottomUp);
        hiddenPanel.setVisibility(View.VISIBLE);
        isPanelShown = true;
    }


//    public void updateDonationsDb(){
//
//        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
//
//        String date = util.generateDate();
//
//        for (int key : donationsMap.keySet()){
//
//            // update external db
//            Donation donation = new Donation(key, donationsMap.get(key), externalId, date);
//            dbref.child("donations").push().setValue(donation);
//
//            // update internal db
//            if(helper.isDataExist(TABLE_E_DONATION, KEY_DONATION_PRODUCT_CODE, String.valueOf(key))){
//                int amount = helper.getDonationAmount(key) + donationsMap.get(key);
//                helper.updateDonation(key, amount);
//            } else {
//                helper.createDonation(key, donationsMap.get(key));
//            }
//        }
//
//        donationsMap.clear();
//
//        Log.d(TAG, "inserted donation to db");
//    }


//    public void displayAdditionalDetailsView(){
//
//        RelativeLayout linearLayout = new RelativeLayout(this);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
//        linearLayout.setLayoutParams(params);
//
//        final EditText phoneEditText = new EditText(this);
//        phoneEditText.setId(View.generateViewId());
//        phoneEditText.setHint("נא להכניס טלפון");
//        RelativeLayout.LayoutParams phoneParams =
//                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                        RelativeLayout.LayoutParams.WRAP_CONTENT);
//        phoneParams.addRule(RelativeLayout.TEXT_ALIGNMENT_CENTER);
//        phoneParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//
//        linearLayout.addView(phoneEditText, phoneParams);
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setTitle("עוד כמה פרטים קטנים");
//        alertDialogBuilder.setView(linearLayout);
//
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton("שלח",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                String phone = phoneEditText.getText().toString();
//
////                                // validate form
////                                if(TextUtils.isEmpty(phone)){
////                                    phoneEditText.setError("required");
////                                } else {
////                                    phoneEditText.setError(null);
////                                }
//
//                                // update user internal details
//                                helper.updateTableField(
//                                        TABLE_E_USER,
//                                        KEY_USER_FIREBASE_ID,
//                                        externalId,
//                                        KEY_USER_PHONE,
//                                        phone
//                                );
//
//                                // update user external details
//                                addExternalUserDetails(phone);
//
//                                updateDonationsDb();
//
//                                finishDonation();
//                            }
//                        })
//                .setNegativeButton("ביטול",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }


    public void finishDonation(){
        Toast finishToast = Toast.makeText(this, "תודה!", Toast.LENGTH_LONG);
        finishToast.show();
//        finish();
    }


    public void addExternalUserDetails(String phone){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        dbref.child("users").child(externalId).child("phone").setValue(phone);
        Log.d(TAG, "added external user details");
    }


//    public boolean needMoreUserDetails(String externalId){
//        User user = helper.getUser();
//        if (user.getPhone() == null){
//            return true;
//        }
//        return false;
//    }


//    public void slideUpDown(final View view) {
//        if (!isPanelShown()) {
//            // Show the panel
//            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
//
//            hiddenPanel.startAnimation(bottomUp);
//            hiddenPanel.setVisibility(View.VISIBLE);
//        }
//        else {
//            // Hide the Panel
//            Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
//
//            hiddenPanel.startAnimation(bottomDown);
//            hiddenPanel.setVisibility(View.GONE);
//        }
//    }
//
//    private boolean isPanelShown() {
//        return hiddenPanel.getVisibility() == View.VISIBLE;
//    }



//    public void showPhoneNumber(){
//        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        String number = tMgr.getLine1Number();
//        Toast.makeText(this, "My Phone Number is: " + number, Toast.LENGTH_SHORT).show();
//    }
}
