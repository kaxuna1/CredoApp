package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

import credo.ge.credoapp.anotations.DateFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/1/17.
 */
@ParserClassAnnotation
public class FamilyPerson extends SugarRecord {
    @ObjectFieldTypeViewAnotation(name = "კავშირი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            requiredForSave = true,
            sqlData = true,
            canAddToDb = false, position = 0,
            filterWith ="56")
    public Dictionary memberType;

    @TextFieldTypeViewAnotation(name = "პირადი ნომერი",
            requiredForSave = true, defaultValue = "", type = "text", mask = "###########", position = 1)
    public String personalNumber;

    @TextFieldTypeViewAnotation(name = "სახელი",hint = "სახელი",
            requiredForSave = true, defaultValue = "",type = "text", position = 2)
    public String name = "";

    @TextFieldTypeViewAnotation(name = "გვარი",hint = "გვარი",
            requiredForSave = true, defaultValue = "",type = "text", position = 3)
    public String surname = "";

    @DateFieldTypeViewAnotation(name = "დაბადების თარიღი",
            requiredForSave = true, position = 4)
    public Date birthDate = new Date();


    @TextFieldTypeViewAnotation(name = "ფაქტობრივი მისამართი",hint = "მისამართი",
            requiredForSave = true, defaultValue = "",type = "text", position = 5)
    public String address;

    @TextFieldTypeViewAnotation(name = "მობილური",
            requiredForSave = true, defaultValue = "",type = "number",mask = "#########", position = 7)
    public String mobile="";
    @TextFieldTypeViewAnotation(name = "ტელეფონი", defaultValue = "",type = "number", position = 8)
    public String phone="";
    @ObjectFieldTypeViewAnotation(name = "სქესი",
            requiredForSave = true, displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 10, filterWith = "3")
    public Dictionary sexType;



    public Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Dictionary getMemberType() {
        return memberType;
    }

    public void setMemberType(Dictionary memberType) {
        this.memberType = memberType;
    }


    public String getName() {
        return name + " " + surname+" "+memberType.getName();
    }

    public static List<Person> getData() {
        return Person.listAll(Person.class);
    }

    public static List<FamilyPerson> findbyperson(long id) {
        return FamilyPerson.find(FamilyPerson.class, "person = ?", id + "");
    }

    public static FamilyPerson getById(long id) {
        return FamilyPerson.findById(FamilyPerson.class, id);
    }

    public String getSurname() {
        return surname;
    }

}
