package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import credo.ge.credoapp.anotations.BooleanFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectsListFieldTypeViewAnottion;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

public class Loan extends SugarRecord<Loan> {
    public String getName() {
        return name;
    }

    @TextFieldTypeViewAnotation(name = "სახელი", deffaultValue = "", type = "text",mask = "###-###-###/##")
    public String name = UUID.randomUUID().toString().substring(0,5);



    @TextFieldTypeViewAnotation(name = "თანხა",
            deffaultValue = "",
            type = "text",visibilityPatern = {"active:true"})
    public String sum = "0";


    @ObjectFieldTypeViewAnotation(name = "პიროვნება",
            displayField = "fullName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = true)
    public Person person;

    @ObjectFieldTypeViewAnotation(name = "თავდები",
            displayField = "fullName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = true,
            visibilityPatern = {"loanType:samomxmareblo"})
    public Person tavdebi;

    @ObjectFieldTypeViewAnotation(name = "ვალუტა", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Currency currency;

    @ObjectFieldTypeViewAnotation(name = "სესხის ტიპი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public LoanType loanType;

    @BooleanFieldTypeViewAnotation(name = "აქტიურია",defaultVal = true)
    public boolean active;

    @ObjectsListFieldTypeViewAnottion(name = "ოჯახის წევრები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = true,
            joinField = "loan")
    public ArrayList<FamilyPerson> family;

    public static List<Loan> getData() {
        return Loan.listAll(Loan.class);
    }

    public static Loan getById(long id) {
        return Loan.findById(Loan.class, id);
    }

    public Loan() {
        family = new ArrayList<>();
        active = false;
    }

}
