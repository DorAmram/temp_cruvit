package com.doramram.cruvit.Objects;


import android.app.Application;

public class GlobalExternalId extends Application{

    private String externalId;

    public String getExternalId(){
        return externalId;
    }

    public void setExternalId(String externalId){
        this.externalId = externalId;
    }

}
