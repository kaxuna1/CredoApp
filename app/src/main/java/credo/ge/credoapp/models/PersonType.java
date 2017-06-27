package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class PersonType extends SugarRecord {
    public String name;


    public String getName() {
        return name;
    }


    public static List<PersonType> getData(){
        return PersonType.listAll(PersonType.class);
    }
    public static PersonType getById(long id){
        return PersonType.findById(PersonType.class,id);
    }
}
