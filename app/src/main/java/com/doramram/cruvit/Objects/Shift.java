package com.doramram.cruvit.Objects;


import java.util.Date;

public class Shift {

    int _id;
    int _startingTime;
    int _endingTime;
    long _date;

    public Shift(){

    }

    public Shift(int _id, int _startingTime, int _endingTime, long _date) {
        this._id = _id;
        this._startingTime = _startingTime;
        this._endingTime = _endingTime;
        this._date = _date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_startingTime() {
        return _startingTime;
    }

    public void set_startingTime(int _startingTime) {
        this._startingTime = _startingTime;
    }

    public int get_endingTime() {
        return _endingTime;
    }

    public void set_endingTime(int _endingTime) {
        this._endingTime = _endingTime;
    }

    public long get_date() {
        return _date;
    }

    public void set_date(long _date) {
        this._date = _date;
    }
}
