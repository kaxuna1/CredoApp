package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/1/17.
 */

public class FamilyPerson extends SugarRecord<FamilyPerson> {
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "kaxa",type = "text")
    public String name;



    @TextFieldTypeViewAnotation(name = "გვარი",deffaultValue = "gelashvili",type = "text")
    public String surname;



    public Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public FamilyMemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(FamilyMemberType memberType) {
        this.memberType = memberType;
    }

    @ObjectFieldTypeViewAnotation(name = "კავშირი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = true)
    public FamilyMemberType memberType;

    public String getName(){
        return name+" "+surname;
    }

    public static List<Person> getData(){
        return Person.listAll(Person.class);
    }
    public static List<FamilyPerson> findbyperson(long id){
        return FamilyPerson.find(FamilyPerson.class,"person = ?",id+"");
    }
    public static FamilyPerson getById(long id){
        return FamilyPerson.findById(FamilyPerson.class,id);
    }
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


}
