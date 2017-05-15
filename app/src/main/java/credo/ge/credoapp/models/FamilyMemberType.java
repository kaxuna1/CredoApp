package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/1/17.
 */

public class FamilyMemberType extends SugarRecord<FamilyMemberType> {

    public int typeId;

    public FamilyMemberType() {
        typeId=0;
        name="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String name;

    public static List<FamilyMemberType> getData(){
        return FamilyMemberType.listAll(FamilyMemberType.class);
    }
    public static FamilyMemberType getById(long id){
        return FamilyMemberType.findById(FamilyMemberType.class,id);
    }



}
