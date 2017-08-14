package com.doramram.cruvit;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.doramram.cruvit.Adapters.ProductAdapter;
import com.doramram.cruvit.Objects.GlobalExternalId;
import com.doramram.cruvit.Objects.Product;
import com.doramram.cruvit.Utils.Util;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseJobActivity extends AppCompatActivity {

    private static final String TAG = "ChooseJobActivity";

    private Util util;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductRecyclerItem> listItems;
    private View hiddenPanel;
    private boolean isPanelShown = false;
    private String externalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_job);


        externalId = ((GlobalExternalId) this.getApplication()).getExternalId();

        util = new Util(this, externalId, TAG);

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
                if(!donationsMap.isEmpty()){

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
}
