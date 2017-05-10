package credo.ge.credoapp.models;

import java.util.List;

import credo.ge.credoapp.ExtensionClasses.DBAdapter;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class Branch extends DBAdapter<Branch> {


    @TextFieldTypeViewAnotation(name = "სახელი", defaultValue = "", type = "text")
    public String branch;

    public String city;

    public String getName() {
        return branch;
    }


    public static List<Branch> getData() {
        return Branch.listAll(Branch.class);
    }

    public static Branch getById(long id) {
        return Branch.findById(Branch.class, id);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
