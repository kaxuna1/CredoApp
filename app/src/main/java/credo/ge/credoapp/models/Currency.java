package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.interfaces.ComboboxBindingInterface;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

public class Currency extends SugarRecord<Currency> implements ComboboxBindingInterface{
    public String getName() {
        return name;
    }

    public String name;

    public static List<Currency> getData(){
        return Currency.listAll(Currency.class);
    }
    public static Currency getById(long id){
        return Currency.findById(Currency.class,id);
    }
}
