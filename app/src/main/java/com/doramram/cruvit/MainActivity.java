package com.doramram.cruvit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.*;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.doramram.cruvit.DB.DataBaseHelper;
import com.doramram.cruvit.Objects.Job;
import com.doramram.cruvit.Objects.NotificationID;
import com.doramram.cruvit.Objects.Product;


//https://github.com/akexorcist/Android-RoundCornerProgressBar

// TODO: add utils class for correctness checking of inputs


public class MainActivity extends AppCompatActivity {

    // Logcat tag
    private static final String TAG = "MainActivity";

    DataBaseHelper helper = new DataBaseHelper(this);
    Intent productIntent;
    Intent jobIntent;
    Intent progressIntent;
    Intent mapIntent;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getPhoneNumber();

//        getContacts();

//        addShortcut();

//        addNotification();

        initializeLocalDb();

        productIntent = new Intent(this, ChooseProductActivity.class);
        jobIntent = new Intent(this, ChooseJobActivity.class);
        progressIntent = new Intent(this, ProgressActivity.class);
        mapIntent = new Intent(this, MapsActivity.class);

//        GridView gridview = (GridView) findViewById(R.id.gridview);
//        gridview.setAdapter(new ImageAdapter(this));
//
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//
//                switch (position) {
//                    case 0:
//                        startActivity(productIntent);
//                        break;
//                    case 1:
//                        startActivity(jobIntent);
//                        break;
//                    case 2:
//                        startActivity(progressIntent);
//                        break;
//                    case 3:
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });


//        final Button button = (Button) findViewById(R.id.button3);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {insertToDb();}
//        });

        final Button productButton = (Button) findViewById(R.id.button_choose_product);
        productButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(productIntent);
            }
        });

        final Button jobButton = (Button) findViewById(R.id.button_choose_job);
        jobButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(jobIntent);
            }
        });

        final Button progressButton = (Button) findViewById(R.id.button_progress);
        progressButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {startActivity(progressIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_donation:
                startActivity(productIntent);
                return true;
            case R.id.reset_db:
                helper.resetDb();
                return true;
            case R.id.help:
                return true;
            case R.id.map:
                startActivity(mapIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public void insertToDb(){
//
////        final FirebaseDatabase database = FirebaseDatabase.getInstance();
////        DatabaseReference ref = database.getReference("server/saving-data/fireblog");
//
//        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
//
//        dbref.child("numbers").push().setValue(1);
//        dbref.child("letters").push().setValue("a");
//
////        Product product = new Product(1, "test_product", "this is my test");
////
////        Map<String, Product> values = new HashMap<>();
////        values.put("myProduct", product);
////
////        dbref.push().setValue(values, new DatabaseReference.CompletionListener(){
////
////            @Override
////            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
////
////                if (databaseError == null){
////                    Log.i("Info", "Save Success");
////                } else {
////                    Log.i("Info", "Save failed");
////                }
////
////            }
////        });
//    }

    public void initializeLocalDb(){

        DataBaseHelper helper = new DataBaseHelper(this);

        Product p1 = new Product(1, "מצות", "חבילת מצות", R.drawable.mazot);
        Product p2 = new Product(2, "קציצות", "סיר קציצות", R.drawable.meatballs);
        Product p3 = new Product(3, "עוף מבושל", "סיר עם עוף", R.drawable.chicken);
        Product p4 = new Product(4, "אנטיפסטי", "", R.drawable.antipasti);
        Product p5 = new Product(5, "english cake", "", R.drawable.english_cake);

        helper.createProduct(p1);
        helper.createProduct(p2);
        helper.createProduct(p3);
        helper.createProduct(p4);
        helper.createProduct(p5);

        Job j1 = new Job(1, "job_name1", "job_description1", "job_hours1", System.currentTimeMillis(), R.drawable.boxes);
        Job j2 = new Job(2, "job_name2", "job_description2", "job_hours2", System.currentTimeMillis(), R.drawable.boxes);
        Job j3 = new Job(3, "job_name3", "job_description3", "job_hours3", System.currentTimeMillis(), R.drawable.boxes);

        helper.createJob(j1);
        helper.createJob(j2);
        helper.createJob(j3);

    }


    public void getContacts(){
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {

                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        Log.v(TAG,  "Name: " + name + ", Phone No: " + phoneNo);

                    }
                    pCur.close();
                }
            }
        }
    }

    private void getPhoneNumber(){
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        String mPhoneId = tMgr.getSubscriberId();

        Log.v(TAG, "phone: " + mPhoneNumber);
        Log.v(TAG, "id: " + mPhoneId);
    }


    private void addShortcut() {
        //Adding shortcut for SignInActivity on Home screen
        Intent shortcutIntent = new Intent(getApplicationContext(), SignInActivity.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Cruvit");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                        R.drawable.cruvit_logo));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        addIntent.putExtra("duplicate", false);  //may it's already there so don't duplicate
        getApplicationContext().sendBroadcast(addIntent);

    }


    public void addNotification(){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.cruvit_logo)
                        .setContentTitle("Cruvit")
                        .setContentText("this is notification from cruvit!");

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ChooseProductActivity.class);

        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ChooseProductActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(NotificationID.getID(), mBuilder.build());

    }

//    private void removeNotification() {
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.cancel(NotificationID.getID());
//    }





//    private void getLocation() {
//
//        // if device running SDK < 23
//        if(Build.VERSION.SDK_INT < 23) {
//
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, locationListener);
//
//        }else{
//
//            // check for location permission
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
//
//            } else {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, locationListener);
//            }
//
//        }
//
//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//
//                Log.v(TAG, location.toString());
//
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//
//        };
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, locationListener);
//            }
//        }
//    }
}
