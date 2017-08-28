package com.doramram.cruvit;

public class JobRecyclerItem extends BaseRecyclerItem{

    String hours;
    long date;

    public JobRecyclerItem(int id, String name, String description, String hours, long date, int image) {
        super(id, name, description, image);
        this.hours = hours;
        this.date = date;
    }

}

