package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

public class Person extends SugarRecord<Person> {
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "kaxa",type = "text")
    public String name;

    @TextFieldTypeViewAnotation(name = "გვარი",deffaultValue = "gelashvili",type = "text")
    public String surname;

    public String fullName() {
        return name + " " + surname;
    }

    public static List<Person> getData(){
        return Person.listAll(Person.class);
    }
    public static Person getById(long id){
        return Person.findById(Person.class,id);
    }
}
