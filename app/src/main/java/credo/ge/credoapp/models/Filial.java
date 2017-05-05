package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class Filial extends SugarRecord<Filial> {
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "",type = "text")
    public String name;


    public String getName() {
        return name;
    }


    public static List<Filial> getData(){
        return Filial.listAll(Filial.class);
    }
    public static Filial getById(long id){
        return Filial.findById(Filial.class,id);
    }
}
