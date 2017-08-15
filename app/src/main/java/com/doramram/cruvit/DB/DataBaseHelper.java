package com.doramram.cruvit.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.doramram.cruvit.Objects.Donator;
import com.doramram.cruvit.Objects.Job;
import com.doramram.cruvit.Objects.Location;
import com.doramram.cruvit.Objects.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.doramram.cruvit.Objects.Shift;
import com.doramram.cruvit.Objects.User;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String TAG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Table Names
    private static final String TABLE_E_PRODUCT = "e_product";
    private static final String TABLE_E_JOB = "e_job";
    private static final String TABLE_E_SHIFT = "e_shift";
    private static final String TABLE_E_LOCATION = "e_location";
    private static final String TABLE_E_DONATION = "e_donation";
    private static final String TABLE_E_DONATOR = "e_donator";
    private static final String TABLE_E_USER = "e_user";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";

    // TABLE_E_PRODUCT - column names
    private static final String KEY_PRODUCT_RECIPE = "recipe";
    private static final String KEY_PRODUCT_AMOUNT = "amount";
    private static final String KEY_PRODUCT_IMAGE = "image";

    // TABLE_E_JOB - column names
//    private static final String KEY_TOOLS = "tools";
//    private static final String KEY_LOCATION_ID = "location_id";
    private static final String KEY_JOB_HOURS = "hours";
    private static final String KEY_JOB_DATE = "date";
    private static final String KEY_JOB_IMAGE = "image";

    // TABLE_E_SHIFT - column names
    private static final String KEY_SHIFT_JOB_CODE = "job_code";
    private static final String KEY_SHIFT_STARTING_TIME = "starting_time";
    private static final String KEY_SHIFT_ENDING_TIME = "ending_time";
    private static final String KEY_SHIFT_DATE = "date";

    // TABLE_E_LOCATION - column names
    private static final String KEY_LOCATION_INSTRUCTIONS = "instructions";

    // TABLE_E_DONATION - column names
    private static final String KEY_DONATION_PRODUCT_CODE = "product_code";
    private static final String KEY_DONATION_PRODUCT_AMOUNT = "product_amount";

    // TABLE_E_DONATOR - column names
    private static final String KEY_DONATOR_PHONE = "donator_phone";
    private static final String KEY_DONATOR_ADDRESS = "donator_address";
    private static final String KEY_DONATOR_SELF_DELIVERY = "self_delivery";
    private static final String KEY_DONATOR_EXTERNAL_ID = "external_id";

    // TABLE_E_USER - column names
    private static final String KEY_USER_FIREBASE_ID = "user_firebase_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PHONE = "user_phone";


    // TABLE_E_PRODUCT table create statement
    private static final String CREATE_TABLE_E_PRODUCT = "CREATE TABLE " + TABLE_E_PRODUCT + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME + " TEXT," +
            KEY_DESCRIPTION + " TEXT," +
            KEY_PRODUCT_RECIPE + " TEXT," +
            KEY_PRODUCT_IMAGE + " INTEGER," +
            KEY_PRODUCT_AMOUNT + " INTEGER" + ")";

    // TABLE_E_JOB table create statement
    private static final String CREATE_TABLE_E_JOB = "CREATE TABLE " + TABLE_E_JOB + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_NAME + " TEXT," +
            KEY_DESCRIPTION + " TEXT," +
            KEY_JOB_HOURS + " TEXT," +
            KEY_JOB_DATE + " TEXT," +
            KEY_JOB_IMAGE + " INTEGER" + ")";

    // TABLE_E_SHIFT table create statement
    private static final String CREATE_TABLE_E_SHIFT = "CREATE TABLE " + TABLE_E_SHIFT + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_SHIFT_JOB_CODE + "INTEGER," +
            KEY_SHIFT_STARTING_TIME + "TEXT," +
            KEY_SHIFT_ENDING_TIME + "TEXT," +
            KEY_SHIFT_DATE + "TEXT" + ")";

    // TABLE_E_LOCATION table create statement
    private static final String CREATE_TABLE_E_LOCATION = "CREATE TABLE " + TABLE_E_LOCATION + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_NAME + " TEXT," +
            KEY_DESCRIPTION + " TEXT," +
            KEY_LOCATION_INSTRUCTIONS + " TEXT" + ")";

    // TABLE_E_DONATION table create statement
    private static final String CREATE_TABLE_E_DONATION = "CREATE TABLE " + TABLE_E_DONATION + "(" +
            KEY_DONATION_PRODUCT_CODE + " INTEGER PRIMARY KEY," +
            KEY_DONATION_PRODUCT_AMOUNT + " INTEGER" + ")";

    // TABLE_E_DONATOR table create statement
    private static final String CREATE_TABLE_E_DONATOR = "CREATE TABLE " + TABLE_E_DONATOR + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_DONATOR_EXTERNAL_ID + " TEXT," +
            KEY_NAME + " TEXT," +
            KEY_DONATOR_PHONE + " TEXT," +
            KEY_DONATOR_ADDRESS + " TEXT," +
            KEY_DONATOR_SELF_DELIVERY + " INTEGER" + ")";

    // TABLE_E_PRODUCT table create statement
    private static final String CREATE_TABLE_E_USER = "CREATE TABLE " + TABLE_E_USER + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_USER_NAME + " TEXT," +
            KEY_USER_FIREBASE_ID + " TEXT," +
            KEY_USER_EMAIL + " TEXT," +
            KEY_USER_PHONE + " TEXT" + ")";

    // TABLE_E_USER SIZE LIMIT QUERY
    private static final String SIZE_LIMIT_TABLE_E_USER = "DELETE FROM " + TABLE_E_USER +
            "WHERE " + KEY_USER_FIREBASE_ID + " NOT IN "+
            "(SELECT " + KEY_USER_FIREBASE_ID + " from " + TABLE_E_USER  +
            " ORDER BY insertion_date DESC LIMIT 1)";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_E_PRODUCT);
        db.execSQL(CREATE_TABLE_E_JOB);
        db.execSQL(CREATE_TABLE_E_LOCATION);
        db.execSQL(CREATE_TABLE_E_SHIFT);
        db.execSQL(CREATE_TABLE_E_DONATION);
        db.execSQL(CREATE_TABLE_E_DONATOR);
        db.execSQL(CREATE_TABLE_E_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_E_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_E_JOB);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_E_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_E_SHIFT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_E_DONATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_E_DONATOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_E_USER);

        // create new tables
        onCreate(db);
    }

    public void resetDb(){
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG, "resetDb called");

        db.execSQL("delete from "+ TABLE_E_USER);
        db.execSQL("delete from "+ TABLE_E_DONATOR);
        db.execSQL("delete from "+ TABLE_E_DONATION);
    }

    public String getTableAsString(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG, "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }

    public boolean isDataExist(String TableName, String dbfield, String fieldValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM " + TableName + " WHERE " + dbfield + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void updateTableField(String tableName, String keyName, String keyValue, String fieldName, String fieldValue){
        SQLiteDatabase db = this.getReadableDatabase();

        String table_string = getTableAsString(tableName);
        Log.d("update_"+tableName, table_string);

        String strSQL = "UPDATE " + tableName +
                " SET " + fieldName + " = '" + fieldValue + "'" +
                " WHERE " + keyName + " = '" + keyValue + "'";
        Log.d("update_"+tableName, strSQL);

        db.execSQL(strSQL);

        table_string = getTableAsString(tableName);
        Log.d("update_"+tableName, table_string);
    }


    // insert product element
    public long createProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, product.get_id());
        values.put(KEY_NAME, product.get_name());
        values.put(KEY_DESCRIPTION, product.get_description());
        values.put(KEY_PRODUCT_RECIPE, product.get_recipe());
        values.put(KEY_PRODUCT_IMAGE, product.get_image());

        long item_id = db.insert(TABLE_E_PRODUCT, null, values);

        return item_id;
    }

    // fetch product element
    public Product getProduct(long product_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_E_PRODUCT + " WHERE "
                + KEY_ID + " = " + product_id;

        Log.e(TAG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product();
        product.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        product.set_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        product.set_description(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
        product.set_recipe(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_RECIPE)));
        product.set_image(cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_IMAGE)));

        return product;
    }

    // fetch all products
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();
        String selectQuery = "SELECT  * FROM " + TABLE_E_PRODUCT;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                product.set_name((cursor.getString(cursor.getColumnIndex(KEY_NAME))));
                product.set_description((cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))));
                product.set_recipe((cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_RECIPE))));
                product.set_image(cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_IMAGE)));

                // adding to todo list
                products.add(product);
            } while (cursor.moveToNext());
        }

        return products;
    }

//    // updating a product
//    public int updateProduct(Product product, String field, String value) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TODO, todo.getNote());
//        values.put(KEY_STATUS, todo.getStatus());
//
//        // updating row
//        return db.update(TABLE_E_PRODUCT, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(product.get_id()) });
//    }


    // deleting a product
    public void deleteProduct(long product_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_E_PRODUCT, KEY_ID + " = ?",
                new String[] { String.valueOf(product_id) });
    }


    // insert job element
    public long createJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, job.get_id());
        values.put(KEY_NAME, job.get_name());
        values.put(KEY_DESCRIPTION, job.get_description());
        values.put(KEY_JOB_HOURS, job.get_hours());
        values.put(KEY_JOB_DATE, job.get_date());
        values.put(KEY_JOB_IMAGE, job.get_image());

        long item_id = db.insert(TABLE_E_JOB, null, values);

        return item_id;
    }

    // fetch job element
    public Job getJob(long job_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_E_JOB + " WHERE "
                + KEY_ID + " = " + job_id;

        Log.e(TAG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        Job job = new Job();
        job.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        job.set_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        job.set_description(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
        job.set_hours(cursor.getString(cursor.getColumnIndex(KEY_JOB_HOURS)));
        job.set_date(cursor.getLong(cursor.getColumnIndex(KEY_JOB_DATE)));
        job.set_image(cursor.getInt(cursor.getColumnIndex(KEY_JOB_IMAGE)));

        return job;
    }

    // fetch all jobs
    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<Job>();
        String selectQuery = "SELECT  * FROM " + TABLE_E_JOB;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Job job = new Job();
                job.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                job.set_name((cursor.getString(cursor.getColumnIndex(KEY_NAME))));
                job.set_description((cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))));
                job.set_hours(cursor.getString(cursor.getColumnIndex(KEY_JOB_HOURS)));
                job.set_date(cursor.getLong(cursor.getColumnIndex(KEY_JOB_DATE)));
                job.set_image(cursor.getInt(cursor.getColumnIndex(KEY_JOB_IMAGE)));

                // adding to todo list
                jobs.add(job);
            } while (cursor.moveToNext());
        }

        return jobs;
    }

//    // updating a product
//    public int updateProduct(Product product, String field, String value) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TODO, todo.getNote());
//        values.put(KEY_STATUS, todo.getStatus());
//
//        // updating row
//        return db.update(TABLE_E_PRODUCT, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(product.get_id()) });
//    }


    // deleting a job
    public void deleteJob(long job_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_E_JOB, KEY_ID + " = ?",
                new String[] { String.valueOf(job_id) });
    }


    // insert location element
    public long createLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, location.get_id());
        values.put(KEY_NAME, location.get_name());
        values.put(KEY_DESCRIPTION, location.get_description());
        values.put(KEY_LOCATION_INSTRUCTIONS, location.get_instructions());

        long item_id = db.insert(TABLE_E_LOCATION, null, values);

        return item_id;
    }

    // fetch location element
    public Location getLocation(long location_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_E_LOCATION + " WHERE "
                + KEY_ID + " = " + location_id;

        Log.e(TAG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        Location location = new Location();
        location.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        location.set_name((cursor.getString(cursor.getColumnIndex(KEY_NAME))));
        location.set_description((cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))));
        location.set_instructions((cursor.getString(cursor.getColumnIndex(KEY_LOCATION_INSTRUCTIONS))));

        return location;
    }

    // fetch all locations
    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<Location>();
        String selectQuery = "SELECT  * FROM " + TABLE_E_LOCATION;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Location location = new Location();
                location.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                location.set_name((cursor.getString(cursor.getColumnIndex(KEY_NAME))));
                location.set_description((cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))));
                location.set_instructions((cursor.getString(cursor.getColumnIndex(KEY_LOCATION_INSTRUCTIONS))));

                // adding to todo list
                locations.add(location);
            } while (cursor.moveToNext());
        }

        return locations;
    }

//    // updating a product
//    public int updateProduct(Product product, String field, String value) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TODO, todo.getNote());
//        values.put(KEY_STATUS, todo.getStatus());
//
//        // updating row
//        return db.update(TABLE_E_PRODUCT, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(product.get_id()) });
//    }

    // deleting a location
    public void deleteLocation(long location_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_E_LOCATION, KEY_ID + " = ?",
                new String[] { String.valueOf(location_id) });
    }


    // insert shift element
    public long createShift(Shift shift) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, shift.get_id());
        values.put(KEY_SHIFT_JOB_CODE, shift.get_jobCode());
        values.put(KEY_SHIFT_STARTING_TIME, shift.get_startingTime());
        values.put(KEY_SHIFT_ENDING_TIME, shift.get_endingTime());
        values.put(KEY_SHIFT_DATE, shift.get_date());

        long item_id = db.insert(TABLE_E_SHIFT, null, values);

        return item_id;
    }

    // fetch shift element
    public Shift getShift(long shift_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_E_SHIFT+ " WHERE "
                + KEY_ID + " = " + shift_id;

        Log.e(TAG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        Shift shift = new Shift();
        shift.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        shift.set_jobCode(cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_JOB_CODE)));
        shift.set_startingTime((cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_STARTING_TIME))));
        shift.set_endingTime((cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_ENDING_TIME))));
        shift.set_date((cursor.getLong(cursor.getColumnIndex(KEY_SHIFT_DATE))));

        return shift;
    }

    // fetch all shifts
    public List<Shift> getAllShifts() {
        List<Shift> shifts = new ArrayList<Shift>();
        String selectQuery = "SELECT  * FROM " + TABLE_E_SHIFT;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Shift shift = new Shift();
                shift.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                shift.set_jobCode(cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_JOB_CODE)));
                shift.set_startingTime((cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_STARTING_TIME))));
                shift.set_endingTime((cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_ENDING_TIME))));
                shift.set_date((cursor.getLong(cursor.getColumnIndex(KEY_SHIFT_DATE))));

                // adding to todo list
                shifts.add(shift);
            } while (cursor.moveToNext());
        }

        return shifts;
    }

//    // updating a product
//    public int updateProduct(Product product, String field, String value) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TODO, todo.getNote());
//        values.put(KEY_STATUS, todo.getStatus());
//
//        // updating row
//        return db.update(TABLE_E_PRODUCT, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(product.get_id()) });
//    }


    // deleting a shift
    public void deleteShift(long shift_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_E_SHIFT, KEY_ID + " = ?",
                new String[] { String.valueOf(shift_id) });
    }


    // insert donation element
    public long createDonation(int product_id, int product_amount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DONATION_PRODUCT_CODE, product_id);
        values.put(KEY_DONATION_PRODUCT_AMOUNT, product_amount);

        long item_id = db.insert(TABLE_E_DONATION, null, values);

        return item_id;
    }

    // insert donations element
    public long createDonations(Map<Integer, Integer> donationsMap) {
        SQLiteDatabase db = this.getWritableDatabase();

        long item_id = 0;
        for(Integer product_id : donationsMap.keySet()){
            int product_amount = donationsMap.get(product_id);

            ContentValues values = new ContentValues();
            values.put(KEY_DONATION_PRODUCT_CODE, product_id);
            values.put(KEY_DONATION_PRODUCT_AMOUNT, product_amount);

            item_id = db.insert(TABLE_E_DONATION, null, values);
        }

        return item_id;
    }

    // fetch donation element
    public int getDonationAmount(long product_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_E_DONATION +
                " WHERE " + KEY_DONATION_PRODUCT_CODE + " = " + product_id;

        Log.e(TAG, selectQuery);
        Log.v(TAG, getTableAsString(TABLE_E_DONATION));

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(cursor.getColumnIndex(KEY_DONATION_PRODUCT_AMOUNT));
    }

    // deleting a donation
    public void deleteDonation(long product_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_E_DONATION, KEY_DONATION_PRODUCT_CODE + " = ?",
                new String[] { String.valueOf(product_id) });
    }

    // update donation element
    public void updateDonation(int product_id, int product_amount){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE " + TABLE_E_DONATION + " SET " +
                KEY_DONATION_PRODUCT_AMOUNT + " = '" + product_amount +
                "' WHERE " + KEY_DONATION_PRODUCT_CODE + " = " + product_id);

        Log.v(TAG, "inserting code: " + product_id + ", amount: " + product_amount);
    }


    // insert donator element
    public long createDonator(Donator donator) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, donator.get_id());
        values.put(KEY_DONATOR_EXTERNAL_ID, donator.get_external_id());
        values.put(KEY_NAME, donator.get_name());
        values.put(KEY_DONATOR_PHONE, donator.get_phone());
        values.put(KEY_DONATOR_ADDRESS, donator.get_address());
        values.put(KEY_DONATOR_SELF_DELIVERY, donator.is_selfDelivery());

        long item_id = db.insert(TABLE_E_DONATOR, null, values);

        return item_id;
    }

    // fetch donator element
    public Donator getDonator() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_E_DONATOR;

        Log.e(TAG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        Donator donator = new Donator();
        donator.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        donator.set_external_id(cursor.getString(cursor.getColumnIndex(KEY_DONATOR_EXTERNAL_ID)));
        donator.set_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        donator.set_phone(cursor.getString(cursor.getColumnIndex(KEY_DONATOR_PHONE)));
        donator.set_address(cursor.getString(cursor.getColumnIndex(KEY_DONATOR_ADDRESS)));
        donator.set_selfDelivery(cursor.getInt(cursor.getColumnIndex(KEY_DONATOR_SELF_DELIVERY)) == 1);

        return donator;
    }

    public boolean isDonatorDetailsExist(){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_E_DONATOR;
        Log.e(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        return (cursor != null);
    }

    public int getTableSize(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();

        String count = "SELECT COUNT(*) FROM " + tableName;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        return icount;
    }

    public boolean isTableEmpty(String tableName){
        return getTableSize(tableName) == 0;
    }

    public void updateDonatorField(String field, String value){
        SQLiteDatabase db = this.getReadableDatabase();

        String table_string = getTableAsString(TABLE_E_DONATOR);
        Log.v("update_donator", table_string);

        String strSQL = "UPDATE " + TABLE_E_DONATOR + " SET " + field + " = '" + value + "' WHERE id = 1";
        Log.v("update_donator", strSQL);

        db.execSQL(strSQL);

        table_string = getTableAsString(TABLE_E_DONATOR);
        Log.v("update_donator", table_string);
    }


    // insert internal user element
    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getUsername());
        values.put(KEY_USER_FIREBASE_ID, user.getFirebaseId());
        values.put(KEY_USER_EMAIL, user.getEmail());
        values.put(KEY_USER_PHONE, user.getPhone());

        long item_id = db.insert(TABLE_E_USER, null, values);
        Log.d(TAG, "inserting new user to e_user");

//        if(getTableSize(TABLE_E_USER) > 1){
//            Log.e(TAG, "table e_user reached it's size limit, reducing it");
//            db.execSQL(SIZE_LIMIT_TABLE_E_USER);
//        }


        return item_id;
    }

    // fetch user element
    public User getUser() {

        SQLiteDatabase db = this.getReadableDatabase();
        User user;

        String selectQuery = "SELECT  * FROM " + TABLE_E_USER;

        Log.e(TAG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
            user.setFirebaseId(cursor.getString(cursor.getColumnIndex(KEY_USER_FIREBASE_ID)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL)));
            user.setPhone(cursor.getString(cursor.getColumnIndex(KEY_USER_PHONE)));
        } else {
            Log.e(TAG, "user table is empty");
            Log.d(TAG, getTableAsString(TABLE_E_USER));
            return null;
        }

        return user;
    }

    // deleting a user
    public void deleteUser(String userFirebaseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_E_USER, KEY_USER_FIREBASE_ID + " = ?",
                new String[] { String.valueOf(userFirebaseId) });
    }

}
