package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;
import java.util.UUID;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

public class Loan extends SugarRecord<Loan> {
    public String getName() {
        return name;
    }

    @TextFieldTypeViewAnotation(name = "სახელი", deffaultValue = "", type = "text")
    public String name = UUID.randomUUID().toString().substring(0,5);
    @TextFieldTypeViewAnotation(name = "თანხა", deffaultValue = "", type = "text")
    public String sum = "0";


    @ObjectFieldTypeViewAnotation(name = "პიროვნება", displayField = "fullName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Person person;
    
    @ObjectFieldTypeViewAnotation(name = "თავდები", displayField = "fullName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Person tavdebi;


    @ObjectFieldTypeViewAnotation(name = "ვალუტა", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public Currency currency;

    @ObjectFieldTypeViewAnotation(name = "სესხის ტიპი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = true)
    public LoanType loanType;

    public static List<Loan> getData(){
        return Loan.listAll(Loan.class);
    }
    public static Loan getById(long id){
        return Loan.findById(Loan.class,id);
    }

}
