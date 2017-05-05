package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class CreditOficer extends SugarRecord<CreditOficer> {
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "",type = "text")
    public String name;


    public String getName() {
        return name;
    }


    public static List<CreditOficer> getData(){
        return CreditOficer.listAll(CreditOficer.class);
    }
    public static CreditOficer getById(long id){
        return CreditOficer.findById(CreditOficer.class,id);
    }
}
