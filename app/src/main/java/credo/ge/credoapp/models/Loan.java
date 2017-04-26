package credo.ge.credoapp.models;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

public class Loan {
    @TextFieldTypeViewAnotation(name = "Name", deffaultValue = "kaxa", type = "text")
    public String name;

    @ObjectFieldTypeViewAnotation(name = "person", displayField = "fullName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Person person;

    o@ObjectFieldTypeViewAnotation(name = "currency", displayField = "name", isMethod = false, type = "comboBox", sqlData = true, canAddToDb = true)
    public Currency currency;
}
