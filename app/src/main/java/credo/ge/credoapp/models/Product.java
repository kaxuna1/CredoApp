package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/27/17.
 */

public class Product extends SugarRecord<Product> {
    public String getName() {
        return product;
    }

    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "",type = "text")
    public String product;

    public static List<Product> getData(){
        return Product.listAll(Product.class);
    }
    public static Product getById(long id){
        return Product.findById(Product.class,id);
    }

    @Override
    public String toString() {
        return product;
    }
}
