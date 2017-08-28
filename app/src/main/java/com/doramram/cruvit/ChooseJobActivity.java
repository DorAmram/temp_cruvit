package com.doramram.cruvit;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.doramram.cruvit.Adapters.JobAdapter;
//import com.doramram.cruvit.Adapters.ProductAdapter;
import com.doramram.cruvit.Objects.GlobalExternalId;
import com.doramram.cruvit.Objects.Job;
//import com.doramram.cruvit.Objects.Product;
import com.doramram.cruvit.Utils.Util;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseJobActivity extends AppCompatActivity {

    private static final String TAG = "ChooseJobActivity";

    private Util util;
    private RecyclerView recyclerView;
    private JobAdapter adapter;
    private List<JobRecyclerItem> listItems;
    private View hiddenPanel;
    private boolean isPanelShown = false;
    public Map<Integer, Integer> donationsMap = new HashMap<Integer, Integer>();
    private String externalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_job);

        externalId = ((GlobalExternalId) this.getApplication()).getExternalId();

        util = new Util(this, externalId, donationsMap, TAG);

        hiddenPanel = findViewById(R.id.job_hidden_panel);
        hiddenPanel.setVisibility(View.INVISIBLE);
        isPanelShown = false;

//        TODO: change view here
        recyclerView = (RecyclerView) findViewById(R.id.job_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        final DataBaseHelper helper = new DataBaseHelper(this);
        List<Job> jobList = util.getHelper().getAllJobs();
        listItems = convertToRecyclerItems(jobList);
        adapter = new JobAdapter(listItems, this);
        recyclerView.setAdapter(adapter);

        // final submit button
        Button submitButton = (Button) findViewById(R.id.job_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!util.getDonationsMap().isEmpty()){

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

    public List<JobRecyclerItem> convertToRecyclerItems(List<Job> jobList){

        List<JobRecyclerItem> jobRecyclerItemList = new ArrayList<JobRecyclerItem>();

        for (Job job:jobList) {
            int id = job.get_id();
            String name = job.get_name();
            String description = job.get_description();
            String hours = job.get_hours();
            long jobDate = job.get_date();
            int image_uri = job.get_image();

            JobRecyclerItem item = new JobRecyclerItem(id, name, description, hours, jobDate, image_uri);
            jobRecyclerItemList.add(item);
        }

        return jobRecyclerItemList;
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

}
