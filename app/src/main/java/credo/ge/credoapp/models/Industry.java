package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class Industry extends SugarRecord<Industry>{
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "",type = "text")
    public String name;


    public String getName() {
        return name;
    }


    public static List<Industry> getData(){
        return Industry.listAll(Industry.class);
    }
    public static Industry getById(long id){
        return Industry.findById(Industry.class,id);
    }

}
