package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

public class Currency extends SugarRecord<Currency>{
    @TextFieldTypeViewAnotation(name = "Name",deffaultValue = "kaxa",type = "text")
    public String name;
}
