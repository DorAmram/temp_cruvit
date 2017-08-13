package com.doramram.cruvit;


public class ProgressRecyclerItem {

    private int _id;
    private String _title;
    private int _currAmount;
    private int _maxAmount;

    public ProgressRecyclerItem() {
    }

    public ProgressRecyclerItem(int _id, String _title, int _currAmount, int _maxAmount) {
        this._id = _id;
        this._title = _title;
        this._currAmount = _currAmount;
        this._maxAmount = _maxAmount;
    }

    public ProgressRecyclerItem(int _id, String _title, int _currAmount) {
        this._id = _id;
        this._title = _title;
        this._currAmount = _currAmount;
        this._maxAmount = 100;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public int get_currAmount() {
        return _currAmount;
    }

    public void set_currAmount(int _currAmount) {
        this._currAmount = _currAmount;
    }

    public int get_maxAmount() {
        return _maxAmount;
    }

    public void set_maxAmount(int _maxAmount) {
        this._maxAmount = _maxAmount;
    }
}
