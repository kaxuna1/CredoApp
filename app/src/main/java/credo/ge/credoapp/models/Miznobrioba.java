package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class Miznobrioba extends SugarRecord<Miznobrioba> {
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "",type = "text")
    public String name;


    public String getName() {
        return name;
    }


    public static List<Miznobrioba> getData(){
        return Miznobrioba.listAll(Miznobrioba.class);
    }
    public static Miznobrioba getById(long id){
        return Miznobrioba.findById(Miznobrioba.class,id);
    }
}
