package com.doramram.cruvit.Objects;


public class Job {

    private int _id;
    private String _name;
    private String _description;
    private String _hours;
    private long _date;
    private int _image;

    public Job(){

    }

    public Job(int _id, String _name, String _description, String hours, long date, int image) {
        this._id = _id;
        this._name = _name;
        this._description = _description;
        this._hours = hours;
        this._date = date;
        this._image = image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_hours() {
        return _hours;
    }

    public void set_hours(String _hours) {
        this._hours = _hours;
    }

    public long get_date() {
        return _date;
    }

    public void set_date(long _date) {
        this._date = _date;
    }

    public int get_image() {
        return _image;
    }

    public void set_image(int _image) {
        this._image = _image;
    }
}
