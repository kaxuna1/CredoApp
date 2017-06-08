package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/1/17.
 */
@ParserClassAnnotation
public class FamilyPerson extends SugarRecord<FamilyPerson> {
    @ObjectFieldTypeViewAnotation(name = "კავშირი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 1,
            filterWith ="56")
    public Dictionary memberType;


    @ObjectFieldTypeViewAnotation(name = "ოჯახის წევრი",
            displayField = "fullName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = true, position = 2)
    public Person joinperson;


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
        return joinperson.name + " " + joinperson.surname+" "+memberType.getName();
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
        return joinperson.surname;
    }

    public Person getJoinPerson() {
        return joinperson;
    }

    public void setJoinPerson(Person joinPerson) {
        this.joinperson = joinPerson;
    }


}
