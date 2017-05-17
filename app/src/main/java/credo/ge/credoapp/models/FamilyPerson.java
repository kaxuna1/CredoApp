package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/1/17.
 */

public class FamilyPerson extends SugarRecord<FamilyPerson> {
    @ObjectFieldTypeViewAnotation(name = "კავშირი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = true, position = 1,
            filterWith ="56")
    public Dictionary memberType;


    @TextFieldTypeViewAnotation(name = "პირადი ნომერი", defaultValue = "kaxa", type = "text", position = 2)
    public String personalNumber;


    @TextFieldTypeViewAnotation(name = "სახელი", defaultValue = "kaxa", type = "text", position = 3)
    public String name;


    @TextFieldTypeViewAnotation(name = "გვარი", defaultValue = "gelashvili", type = "text", position = 4)
    public String surname;

    @TextFieldTypeViewAnotation(name = "დაბადების თარიღი", defaultValue = "",type = "text",mask = "##/##/####", position = 5)
    public String birthDate;


    @TextFieldTypeViewAnotation(name = "ფაქტობრივი მისამართი", defaultValue = "",type = "text", position = 6)
    public String address;

    @TextFieldTypeViewAnotation(name = "მობილური", defaultValue = "",type = "text",mask = "(5##)-###-###", position = 7)
    public String mobile;

    @ObjectFieldTypeViewAnotation(name = "სქესი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 10, filterWith = "3")
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
        return name + " " + surname;
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

    public void setSurname(String surname) {
        this.surname = surname;
    }


}
