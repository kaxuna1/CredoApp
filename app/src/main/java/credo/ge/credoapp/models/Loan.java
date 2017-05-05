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
        return person!=null?person.fullName():"დაუსრულებელი სესხი";
    }





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




    @ObjectFieldTypeViewAnotation(name = "ოფიცერი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public CreditOficer creditOficer;
    @BooleanFieldTypeViewAnotation(name = "აქტიურია",defaultVal = true)
    public boolean active;


    @ObjectFieldTypeViewAnotation(name = "ფილიალი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Filial filial;
    @ObjectFieldTypeViewAnotation(name = "პროდუქტი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public LoanType loanType;
    @ObjectFieldTypeViewAnotation(name = "სოფელი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Vilage vilage;
    @ObjectFieldTypeViewAnotation(name = "სოფლის კონსული", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public VilageCounsel vilageCounsel;
    @ObjectFieldTypeViewAnotation(name = "მიზნობრიობა", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Miznobrioba miznobrioba;
    @TextFieldTypeViewAnotation(name = "გამოცდილება",deffaultValue = "1",type = "int")
    public int gamocdileba;
    @TextFieldTypeViewAnotation(name = "თანხა",deffaultValue = "1",type = "int")
    public int loanSum;
    @ObjectFieldTypeViewAnotation(name = "ვალუტა", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Currency currency;

    @TextFieldTypeViewAnotation(name = "სესხის ვადა",deffaultValue = "1",type = "int")
    public int loanDuration;

    @TextFieldTypeViewAnotation(name = "კომენტარი", deffaultValue = "", type = "text")
    public String comment;


    @TextFieldTypeViewAnotation(name = "ცხოველების რაოდენობა",deffaultValue = "1",type = "int",
            visibilityPatern = {"loanType:აგრო"})
    public int numberOfAnimals;
    @TextFieldTypeViewAnotation(name = "მიწა (ჰა)",deffaultValue = "1",type = "int",
            visibilityPatern = {"loanType:აგრო"})
    public int mitsa;



    public static List<Loan> getData() {
        return Loan.listAll(Loan.class);
    }

    public static Loan getById(long id) {
        return Loan.findById(Loan.class, id);
    }

    public Loan() {
        active = false;
    }

}
