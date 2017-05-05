package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class SexType extends SugarRecord<SexType> {
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "",type = "text")
    public String name;


    public String getName() {
        return name;
    }


    public static List<SexType> getData(){
        return SexType.listAll(SexType.class);
    }
    public static SexType getById(long id){
        return SexType.findById(SexType.class,id);
    }
}
