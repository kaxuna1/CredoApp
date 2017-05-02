package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/1/17.
 */

public class FamilyPerson extends SugarRecord<FamilyPerson> {
    @ObjectFieldTypeViewAnotation(name = "პიროვნება", displayField = "fullName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Person person;



    public Loan loan;

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
        return person.fullName()+" "+memberType.name;
    }

    public static List<Person> getData(){
        return Person.listAll(Person.class);
    }
    public static List<FamilyPerson> findByLoan(long id){
        return FamilyPerson.find(FamilyPerson.class,"loan = ?",id+"");
    }
    public static FamilyPerson getById(long id){
        return FamilyPerson.findById(FamilyPerson.class,id);
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
