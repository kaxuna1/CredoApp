package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class VilageCounsel extends SugarRecord<VilageCounsel> {
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "",type = "text")
    public String name;


    public String getName() {
        return name;
    }


    public static List<VilageCounsel> getData(){
        return VilageCounsel.listAll(VilageCounsel.class);
    }
    public static VilageCounsel getById(long id){
        return CreditOficer.findById(VilageCounsel.class,id);
    }
}
