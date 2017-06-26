package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import credo.ge.credoapp.anotations.DateFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectsListFieldTypeViewAnottion;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */
@ParserClassAnnotation(cols = {"მომხმარებელი","ოჯახის წევრები"})
public class Person extends SugarRecord<Person> {
    @TextFieldTypeViewAnotation(name = "პირადი ნომერი",required=true,requiredForSave=true, defaultValue = "", type = "text", mask = "###########", position = 1)
    public String personalNumber;

    @TextFieldTypeViewAnotation(name = "სახელი",required=true,requiredForSave=true, defaultValue = "",type = "text", position = 2)
    public String name = "";

    @TextFieldTypeViewAnotation(name = "გვარი",required=true,requiredForSave=true, defaultValue = "",type = "text", position = 3)
    public String surname = "";

    @DateFieldTypeViewAnotation(name = "დაბადების თარიღი",requiredForSave=true,  position = 4)
    public Date birthDate = new Date();


    @TextFieldTypeViewAnotation(name = "ფაქტობრივი მისამართი",requiredForSave=true, defaultValue = "",type = "text", position = 5)
    public String address;

    @ObjectFieldTypeViewAnotation(name = "ტიპი", displayField = "getName",requiredForSave=true, isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 6,filterWith = "13")
    public Dictionary personType;

    @TextFieldTypeViewAnotation(name = "მობილური", defaultValue = "",requiredForSave=true,type = "number",mask = "(5##)-###-###", position = 7)
    public String mobile;

    @TextFieldTypeViewAnotation(name = "ტელეფონი", defaultValue = "",type = "text", position = 8)
    public String phone;
    @TextFieldTypeViewAnotation(name = "დამოკიდებულ პირთა რაოდენობა", defaultValue = "",type = "text", position = 9)
    public String connectedPersonsNumber;


    @ObjectFieldTypeViewAnotation(name = "სქესი", displayField = "getName",requiredForSave=true, isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 10, filterWith = "3")
    public Dictionary sexType;

    @ObjectFieldTypeViewAnotation(name = "ოჯახური მდგომარეობა",
            displayField = "getName", isMethod = true,
            type = "comboBox", sqlData = true,
            canAddToDb = false, position = 11,
            filterWith = "100")
    public Dictionary ojaxuriMdgomareoba;


    @ObjectFieldTypeViewAnotation(name = "სექტორი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 12,filterWith = "101")
    public Dictionary sector;

    @ObjectFieldTypeViewAnotation(name = "ინდუსტრია", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false,
            position = 13)
    public Industry industry;









    @ObjectsListFieldTypeViewAnottion(name = "ოჯახის წევრები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "person", position = 14,page = 1)
    public List<FamilyPerson> family;

    public String fullName() {
        return (name==""?"სახელი":name) + " " + (surname==""?"გვარი":surname);
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

    public List<FamilyPerson> getFamily() {
        return FamilyPerson.findbyperson(this.id);
    }

    public void setFamily(ArrayList<FamilyPerson> family) {
        this.family = family;
    }

    @Override
    public String toString() {
        return personalNumber == null? "":personalNumber;
    }
}
