package com.doramram.cruvit.Objects;


public class Donator {

    private int _id = 1;
    private String _external_id;
    private String _name;
    private String _phone;
    private String _address;
    private boolean _selfDelivery;

    public Donator(){

    }

    public Donator(String _name, String _phone, String _address) {
        this._name = _name;
        this._phone = _phone;
        this._address = _address;
    }

    public Donator(int id, String name, String phone, String address){
        _id = id;
        _name = name;
        _phone = phone;
        _address = address;
        _selfDelivery = false;
    }

    public Donator(int id, String name, String phone, String address, boolean selfDelivery){
        _id = id;
        _name = name;
        _phone = phone;
        _address = address;
        _selfDelivery = selfDelivery;
    }

    public Donator(String _external_id, String _name, String _phone, String _address) {
        this._external_id = _external_id;
        this._name = _name;
        this._phone = _phone;
        this._address = _address;
    }

    public Donator(String _external_id, String _name, String _phone, String _address, boolean _selfDelivery) {
        this._external_id = _external_id;
        this._name = _name;
        this._phone = _phone;
        this._address = _address;
        this._selfDelivery = _selfDelivery;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {this._id = _id;}

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public boolean is_selfDelivery() {
        return _selfDelivery;
    }

    public void set_selfDelivery(boolean _selfDelivery) {
        this._selfDelivery = _selfDelivery;
    }

    public String get_external_id() {return _external_id;}

    public void set_external_id(String _external_id) {this._external_id = _external_id;}
}
