package credo.ge.credoapp.models;


import android.util.Log;

import credo.ge.credoapp.anotations.DateFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/25/17.
 */

public class TestModel {
    @TextFieldTypeViewAnotation(name = "Name",deffaultValue = "kaxa",type = "text")
    public String name;
    @TextFieldTypeViewAnotation(name = "Surname",deffaultValue = "gelashvili",type = "text")
    public String surname;

    public void kaxa(String k){
        Log.d(k,k);
    }
}
