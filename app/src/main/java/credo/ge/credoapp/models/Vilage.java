package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class Vilage extends SugarRecord {
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "",type = "text")
    public String name;


    public String getName() {
        return name;
    }


    public static List<Vilage> getData(){
        return Vilage.listAll(Vilage.class);
    }
    public static Vilage getById(long id){
        return Vilage.findById(Vilage.class,id);
    }
}
