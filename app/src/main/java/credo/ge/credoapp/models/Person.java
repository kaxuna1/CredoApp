package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectsListFieldTypeViewAnottion;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

public class Person extends SugarRecord<Person> {
    @TextFieldTypeViewAnotation(name = "პირადი ნომერი", defaultValue = "",type = "text",mask = "###########")
    public String personalNumber;

    @TextFieldTypeViewAnotation(name = "სახელი", defaultValue = "",type = "text")
    public String name;

    @TextFieldTypeViewAnotation(name = "გვარი", defaultValue = "",type = "text")
    public String surname;

    @TextFieldTypeViewAnotation(name = "დაბადების თარიღი", defaultValue = "",type = "text",mask = "##/##/####")
    public String birthDate;


    @TextFieldTypeViewAnotation(name = "ფაქტობრივი მისამართი", defaultValue = "",type = "text")
    public String address;

    @ObjectFieldTypeViewAnotation(name = "ტიპი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public PersonType personType;

    @TextFieldTypeViewAnotation(name = "მობილური", defaultValue = "",type = "text",mask = "(5##)-###-###")
    public String mobile;

    @TextFieldTypeViewAnotation(name = "ტელეფონი", defaultValue = "",type = "text")
    public String phone;
    @TextFieldTypeViewAnotation(name = "დამოკიდებულ პირთა რაოდენობა", defaultValue = "",type = "text")
    public String connectedPersonsNumber;


    @ObjectFieldTypeViewAnotation(name = "სქესი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public SexType sexType;

    @ObjectFieldTypeViewAnotation(name = "ოჯახური მდგომარეობა", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public OjaxuriMdgomareoba ojaxuriMdgomareoba;


    @ObjectFieldTypeViewAnotation(name = "სექტორი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Sector sector;

    @ObjectFieldTypeViewAnotation(name = "ინდუსტრია", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Industry industry;








    @ObjectsListFieldTypeViewAnottion(name = "ოჯახის წევრები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = true,
            joinField = "person")
    public ArrayList<FamilyPerson> family;

    public String fullName() {
        return name + " " + surname;
    }

    public static List<Person> getData(){
        return Person.listAll(Person.class);
    }
    public static Person getById(long id){
        return Person.findById(Person.class,id);
    }


    public Person(){
        family=new ArrayList<>();
    }
}
