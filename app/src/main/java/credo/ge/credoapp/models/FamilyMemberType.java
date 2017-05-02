package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/1/17.
 */

public class FamilyMemberType extends SugarRecord<FamilyMemberType> {

    @TextFieldTypeViewAnotation(name = "ტიპის id",deffaultValue = "1",type = "text")
    public int typeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "name",type = "text")
    public String name;





}
