package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

public class Loan extends SugarRecord<Loan> {
    @TextFieldTypeViewAnotation(name = "სახელი", deffaultValue = "", type = "text")
    public String name;

    @ObjectFieldTypeViewAnotation(name = "პიროვნება", displayField = "fullName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Person person;


    @ObjectFieldTypeViewAnotation(name = "ვალუტა", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Currency currency;
}
