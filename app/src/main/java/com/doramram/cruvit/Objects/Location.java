package com.doramram.cruvit.Objects;


public class Location {

    int _id;
    String _name;
    String _description;
    String _instructions;

    public Location(){

    }

    public Location(int _id, String _name) {
        this._id = _id;
        this._name = _name;
    }

    public Location(int _id, String _name, String _description) {
        this._id = _id;
        this._name = _name;
        this._description = _description;
    }

    public Location(int _id, String _name, String _description, String _instructions) {
        this._id = _id;
        this._name = _name;
        this._description = _description;
        this._instructions = _instructions;
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

    public String get_instructions() {
        return _instructions;
    }

    public void set_instructions(String _instructions) {
        this._instructions = _instructions;
    }
}
