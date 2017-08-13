package com.doramram.cruvit.Objects;

import java.sql.Time;
import java.util.Date;

public class Donation {

    int _productId;
    int _amount;
    String _donatorId;
    String _date;

    public Donation(){

    }

    public Donation(int _productId, int _amount, String _donatorId) {
        this._productId = _productId;
        this._amount = _amount;
        this._donatorId = _donatorId;
    }

    public Donation(int _productId, int _amount, String _donatorId, String date) {
        this._productId = _productId;
        this._amount = _amount;
        this._donatorId = _donatorId;
        this._date = date;
    }

    public int get_productId() {
        return _productId;
    }

    public void set_productId(int _productId) {
        this._productId = _productId;
    }

    public int get_amount() {
        return _amount;
    }

    public void set_amount(int _amount) {
        this._amount = _amount;
    }

    public String get_donatorId() {
        return _donatorId;
    }

    public void set_donatorId(String _donatorId) {
        this._donatorId = _donatorId;
    }

    public String get_date() {return _date;}

    public void set_date(String date) {this._date = date;}
}
