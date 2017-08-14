package com.doramram.cruvit.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.doramram.cruvit.DB.DataBaseHelper;
import com.doramram.cruvit.Objects.Donation;
import com.doramram.cruvit.Objects.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Util {

    private static final String TABLE_E_USER = "e_user";
    private static final String TABLE_E_DONATOR = "e_donator";
    private static final String TABLE_E_DONATION = "e_donation";
    private static final String KEY_DONATION_PRODUCT_CODE = "product_code";
    private static final String KEY_DONATION_PRODUCT_AMOUNT = "product_amount";
    private static final String KEY_USER_FIREBASE_ID = "user_firebase_id";
    private static final String KEY_USER_PHONE = "user_phone";

    Context _context;
    String _externalId;
    String _tag;
    DataBaseHelper helper;
    Map<Integer, Integer> donationsMap = new HashMap<Integer, Integer>();

    public Util(Context context, String externalId, String tag){
        this._context = context;
        this._externalId = externalId;
        this._tag = tag;
        this.helper = new DataBaseHelper(context);
    }


    public void displayAdditionalDetailsView(){

        RelativeLayout linearLayout = new RelativeLayout(this._context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        linearLayout.setLayoutParams(params);

        final EditText phoneEditText = new EditText(this._context);
        phoneEditText.setId(View.generateViewId());
        phoneEditText.setHint("נא להכניס טלפון");
        RelativeLayout.LayoutParams phoneParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        phoneParams.addRule(RelativeLayout.TEXT_ALIGNMENT_CENTER);
        phoneParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        linearLayout.addView(phoneEditText, phoneParams);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this._context);
        alertDialogBuilder.setTitle("עוד כמה פרטים קטנים");
        alertDialogBuilder.setView(linearLayout);

        final String externalId = this._externalId;

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("שלח",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String phone = phoneEditText.getText().toString();

//                                // validate form
//                                if(TextUtils.isEmpty(phone)){
//                                    phoneEditText.setError("required");
//                                } else {
//                                    phoneEditText.setError(null);
//                                }

                                // update user internal details
                                helper.updateTableField(
                                        TABLE_E_USER,
                                        KEY_USER_FIREBASE_ID,
                                        externalId,
                                        KEY_USER_PHONE,
                                        phone
                                );

                                // update user external details
                                addExternalUserDetails(phone);

                                updateDonationsDb();

                                finishDonation();
                            }
                        })
                .setNegativeButton("ביטול",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void updateDonationsDb(){

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();

        String date = generateDate();

        for (int key : donationsMap.keySet()){

            // update external db
            Donation donation = new Donation(key, donationsMap.get(key), this._externalId, date);
            dbref.child("donations").push().setValue(donation);

            // update internal db
            if(helper.isDataExist(TABLE_E_DONATION, KEY_DONATION_PRODUCT_CODE, String.valueOf(key))){
                int amount = helper.getDonationAmount(key) + donationsMap.get(key);
                helper.updateDonation(key, amount);
            } else {
                helper.createDonation(key, donationsMap.get(key));
            }
        }

        donationsMap.clear();

        Log.d(this._tag, "inserted donation to db");
    }

    public void addExternalUserDetails(String phone){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        dbref.child("users").child(this._externalId).child("phone").setValue(phone);
        Log.d(this._tag, "added external user details");
    }

    public void finishDonation(){
        Toast finishToast = Toast.makeText(this._context, "תודה!", Toast.LENGTH_LONG);
        finishToast.show();
//        finish();
    }


    public String generateDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(calendar.getTime());

        return formattedDate;
    }

    public boolean needMoreUserDetails(String externalId){
        User user = helper.getUser();
        if (user.getPhone() == null){
            return true;
        }
        return false;
    }

    public Context get_context() {
        return _context;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }

    public String get_externalId() {
        return _externalId;
    }

    public void set_externalId(String _externalId) {
        this._externalId = _externalId;
    }

    public String get_tag() {
        return _tag;
    }

    public void set_tag(String _tag) {
        this._tag = _tag;
    }

    public DataBaseHelper getHelper() {
        return helper;
    }

    public void setHelper(DataBaseHelper helper) {
        this.helper = helper;
    }

    public Map<Integer, Integer> getDonationsMap() {
        return donationsMap;
    }

    public void setDonationsMap(Map<Integer, Integer> donationsMap) {
        this.donationsMap = donationsMap;
    }
}
