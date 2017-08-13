package com.doramram.cruvit;


//https://stackoverflow.com/questions/26890190/images-in-recyclerview

public class ProductRecyclerItem {

    private int _id;
    private String _title;
    private String _description;
    private int _image;

    public ProductRecyclerItem(int id, String title, String description, int image) {
        this._id = id;
        this._title = title;
        this._description = description;
        this._image = image;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public int get_id() {return _id;}

    public void set_id(int _id) {this._id = _id;}

    public int get_image() {return _image;}

    public void set_image(int _image) {this._image = _image;}
}
