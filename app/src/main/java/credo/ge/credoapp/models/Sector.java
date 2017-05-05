package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class Sector extends SugarRecord<Sector> {
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "",type = "text")
    public String name;


    public String getName() {
        return name;
    }


    public static List<Sector> getData(){
        return Sector.listAll(Sector.class);
    }
    public static Sector getById(long id){
        return Sector.findById(Sector.class,id);
    }
}
