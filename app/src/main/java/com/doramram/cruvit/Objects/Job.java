package com.doramram.cruvit.Objects;


public class Job {

    private int _id;
    private String _name;
    private String _description;
    private String _tools;
    private int _location_id;

    public Job(){

    }

    public Job(int _id, String _name, String _description) {
        this._id = _id;
        this._name = _name;
        this._description = _description;
    }

    public Job(int _id, String _name, String _description, String _tools, int _location_id) {
        this._id = _id;
        this._name = _name;
        this._description = _description;
        this._tools = _tools;
        this._location_id = _location_id;
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

    public String get_tools() {
        return _tools;
    }

    public void set_tools(String _tools) {
        this._tools = _tools;
    }

    public int get_location_id() {
        return _location_id;
    }

    public void set_location_id(int _location_id) {
        this._location_id = _location_id;
    }
}
