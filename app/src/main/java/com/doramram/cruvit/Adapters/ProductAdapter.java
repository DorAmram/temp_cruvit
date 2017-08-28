package com.doramram.cruvit.Adapters;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.DialogInterface;


import com.doramram.cruvit.ChooseProductActivity;
import com.doramram.cruvit.ProductRecyclerItem;
import com.doramram.cruvit.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doramram.cruvit.DB.DataBaseHelper;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<ProductRecyclerItem> listItems;
    private Context mContext;
    private Map<Integer, Integer> donations = new HashMap<Integer, Integer>();
    DataBaseHelper helper = new DataBaseHelper(mContext);

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtId;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtId = (TextView) itemView.findViewById(R.id.txtId);
            imageView = (ImageView) itemView.findViewById(R.id.product_image);
        }
    }

    public ProductAdapter(List<ProductRecyclerItem> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_recycler_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(viewGroup);
        final Context context = parent.getContext();

        viewGroup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            RelativeLayout linearLayout = new RelativeLayout(mContext);
            final NumberPicker aNumberPicker = new NumberPicker(mContext);
            aNumberPicker.setMaxValue(20);
            aNumberPicker.setMinValue(1);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
            RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

            linearLayout.setLayoutParams(params);
            linearLayout.addView(aNumberPicker,numPicerParams);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setTitle("כמה תרצו לתרום?");
            alertDialogBuilder.setView(linearLayout);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("לתרום",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    int productId = Integer.parseInt(viewHolder.txtId.getText().toString());
                                    int productAmount = aNumberPicker.getValue();
                                    if(mContext instanceof ChooseProductActivity){
                                        ((ChooseProductActivity)mContext).updateDonationsMap(productId, productAmount);
                                    }
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
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductRecyclerItem item = listItems.get(position);
        holder.txtTitle.setText(item.get_title());
        holder.txtDescription.setText(item.get_description());
        holder.txtId.setText(String.valueOf(item.get_id()));
        holder.imageView.setImageResource(item.get_image());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

}
