package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import credo.ge.credoapp.anotations.BooleanFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.LabelFieldTypeViewAnotaion;
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


    @ObjectFieldTypeViewAnotation(name = "პიროვნება",
            displayField = "fullName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 1)
    public Person person;




    @ObjectFieldTypeViewAnotation(name = "ფილიალი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 2)
    public Branch branch;


    @ObjectFieldTypeViewAnotation(name = "ოფიცერი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 3)
    public LoanOficer loanOficer;
    @ObjectFieldTypeViewAnotation(name = "პროდუქტი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 4)
    public Product product;
    @ObjectFieldTypeViewAnotation(name = "სოფელი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 5)
    public Vilage vilage;
    @ObjectFieldTypeViewAnotation(name = "სოფლის კონსული", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 6)
    public VilageCounsel vilageCounsel;
    @ObjectFieldTypeViewAnotation(name = "მიზნობრიობა", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 7)
    public Purpose purpose;
    @TextFieldTypeViewAnotation(name = "გამოცდილება", defaultValue = "1",type = "int", position = 8)
    public int gamocdileba;
    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 9)
    public int loanSum;
    @ObjectFieldTypeViewAnotation(name = "ვალუტა", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 10,filterWith="16")
    public Dictionary currency;

    @TextFieldTypeViewAnotation(name = "სესხის ვადა", defaultValue = "1",type = "int", position = 11)
    public int loanDuration;

    @TextFieldTypeViewAnotation(name = "კომენტარი", defaultValue = "", type = "text", position = 12)
    public String comment;


    @TextFieldTypeViewAnotation(name = "ცხოველების რაოდენობა", defaultValue = "1",type = "int",
            visibilityPatern = {"product:აგრო"}, position = 13)
    public int numberOfAnimals;
    @TextFieldTypeViewAnotation(name = "მიწა (ჰა)", defaultValue = "1",type = "int",
            visibilityPatern = {"product:აგრო"}, position = 14)
    public int mitsa;

    @LabelFieldTypeViewAnotaion(label = "ანალიტიკა",position = 15)
    public String label0;


    @ObjectsListFieldTypeViewAnottion(name = "აგრო პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 16)
    public ArrayList<AgroProduct> agroProducts;
    @ObjectsListFieldTypeViewAnottion(name = "ურბანული პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 17)
    public ArrayList<UrbaProduct> urbaProducts;




    public static List<Loan> getData() {
        return Loan.listAll(Loan.class);
    }

    public static Loan getById(long id) {
        return Loan.findById(Loan.class, id);
    }

    public Loan() {

    }

}
