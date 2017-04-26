package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

public class Person extends SugarRecord<Person> {
    @TextFieldTypeViewAnotation(name = "Name",deffaultValue = "kaxa",type = "text")
    public String name;

    @TextFieldTypeViewAnotation(name = "Surname",deffaultValue = "gelashvili",type = "text")
    public String surname;

    public String fullName() {
        return name + " " + surname;
    }
}
