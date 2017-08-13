package com.doramram.cruvit.Objects;


public class Product {
    
    private int _id;
    private String _name;
    private String _description;
    private String _recipe;
    private int _image;


    public Product(){

    }

    public Product(int id, String name){
        _id = id;
        _name = name;
        _description = null;
        _recipe = null;
    }

    public Product(int id, String name, String description){
        _id = id;
        _name = name;
        _description = description;
        _recipe = null;
    }

    public Product(int id, String name, String description, int image){
        _id = id;
        _name = name;
        _description = description;
        _image = image;
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

    public String get_recipe() {
        return _recipe;
    }

    public void set_recipe(String _recipe) {
        this._recipe = _recipe;
    }

    public int get_image() {return _image;}

    public void set_image(int _image) {this._image = _image;}
}
