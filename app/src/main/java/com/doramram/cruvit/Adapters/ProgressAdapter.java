package com.doramram.cruvit.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import com.doramram.cruvit.DB.DataBaseHelper;
import com.doramram.cruvit.ProgressRecyclerItem;
import com.doramram.cruvit.R;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder>{

    private List<ProgressRecyclerItem> listItems;
    private Context mContext;
    DataBaseHelper helper = new DataBaseHelper(mContext);

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView progressTxtTitle;
        public TextView progressTxtStatus;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            progressTxtTitle = (TextView) itemView.findViewById(R.id.item_progress_title);
            progressTxtStatus = (TextView) itemView.findViewById(R.id.item_progress_status);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_progress_bar);
        }
    }

    public ProgressAdapter(List<ProgressRecyclerItem> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @Override
    public ProgressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_recycler_item, parent, false);

        final ProgressAdapter.ViewHolder viewHolder = new ProgressAdapter.ViewHolder(viewGroup);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProgressAdapter.ViewHolder holder, int position) {
        final ProgressRecyclerItem item = listItems.get(position);
        holder.progressTxtTitle.setText(item.get_title());
        int currAmount = item.get_currAmount();
        int maxAmount = item.get_maxAmount();
        holder.progressTxtStatus.setText(currAmount + "/" + maxAmount);
        holder.progressBar.setMax(maxAmount);
        holder.progressBar.setProgress(currAmount);
    }

    @Override
    public int getItemCount() {return listItems.size();}
}
