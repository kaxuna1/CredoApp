package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectsListFieldTypeViewAnottion;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */
@ParserClassAnnotation(cols = {"მომხმარებელი","ოჯახის წევრები"})
public class Person extends SugarRecord<Person> {
    @TextFieldTypeViewAnotation(name = "პირადი ნომერი", defaultValue = "", type = "text", mask = "###########", position = 1)
    public String personalNumber;

    @TextFieldTypeViewAnotation(name = "სახელი", defaultValue = "",type = "text", position = 2)
    public String name = "";

    @TextFieldTypeViewAnotation(name = "გვარი", defaultValue = "",type = "text", position = 3)
    public String surname = "";

    @TextFieldTypeViewAnotation(name = "დაბადების თარიღი", defaultValue = "",type = "text",mask = "##/##/####", position = 4)
    public String birthDate;


    @TextFieldTypeViewAnotation(name = "ფაქტობრივი მისამართი", defaultValue = "",type = "text", position = 5)
    public String address;

    @ObjectFieldTypeViewAnotation(name = "ტიპი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 6,filterWith = "13")
    public Dictionary personType;

    @TextFieldTypeViewAnotation(name = "მობილური", defaultValue = "",type = "number",mask = "(5##)-###-###", position = 7)
    public String mobile;

    @TextFieldTypeViewAnotation(name = "ტელეფონი", defaultValue = "",type = "text", position = 8)
    public String phone;
    @TextFieldTypeViewAnotation(name = "დამოკიდებულ პირთა რაოდენობა", defaultValue = "",type = "text", position = 9)
    public String connectedPersonsNumber;


    @ObjectFieldTypeViewAnotation(name = "სქესი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 10, filterWith = "3")
    public Dictionary sexType;

    @ObjectFieldTypeViewAnotation(name = "ოჯახური მდგომარეობა",
            displayField = "getName", isMethod = true,
            type = "comboBox", sqlData = true,
            canAddToDb = false, position = 11,
            filterWith = "44")
    public Dictionary ojaxuriMdgomareoba;


    @ObjectFieldTypeViewAnotation(name = "სექტორი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 12,filterWith = "1100")
    public Dictionary sector;

    @ObjectFieldTypeViewAnotation(name = "ინდუსტრია", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 13,filterWith = "1101")
    public Dictionary industry;









    @ObjectsListFieldTypeViewAnottion(name = "ოჯახის წევრები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "person", position = 14,page = 1)
    public ArrayList<FamilyPerson> family;

    public String fullName() {
        return (name==null?"სახელი":name) + " " + (surname==null?"გვარი":surname);
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
