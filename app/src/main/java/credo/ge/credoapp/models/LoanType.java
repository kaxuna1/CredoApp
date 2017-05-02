package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/27/17.
 */

public class LoanType extends SugarRecord<LoanType> {
    public String getName() {
        return name;
    }

    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "",type = "text")
    public String name;

    public static List<LoanType> getData(){
        return LoanType.listAll(LoanType.class);
    }
    public static LoanType getById(long id){
        return LoanType.findById(LoanType.class,id);
    }

    @Override
    public String toString() {
        return name;
    }
}
