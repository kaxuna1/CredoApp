package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class OjaxuriMdgomareoba extends SugarRecord<OjaxuriMdgomareoba> {
    @TextFieldTypeViewAnotation(name = "სახელი", defaultValue = "",type = "text")
    public String name;


    public String getName() {
        return name;
    }


    public static List<OjaxuriMdgomareoba> getData(){
        return OjaxuriMdgomareoba.listAll(OjaxuriMdgomareoba.class);
    }
    public static OjaxuriMdgomareoba getById(long id){
        return OjaxuriMdgomareoba.findById(OjaxuriMdgomareoba.class,id);
    }
}
