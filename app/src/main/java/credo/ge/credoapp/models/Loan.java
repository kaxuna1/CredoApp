package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import credo.ge.credoapp.anotations.DataGroupContainerTypeViewAnotation;
import credo.ge.credoapp.anotations.DataGroupFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectsListFieldTypeViewAnottion;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.analysis.AgroProductType;
import credo.ge.credoapp.models.analysis.Balance;
import credo.ge.credoapp.models.analysis.BusinesExpanse;
import credo.ge.credoapp.models.analysis.BusinessBalance;
import credo.ge.credoapp.models.analysis.FamilyExpanse;
import credo.ge.credoapp.models.analysis.OtherExpanse;
import credo.ge.credoapp.models.analysis.OtherIncomeType;
import credo.ge.credoapp.models.analysis.PersonalBalance;
import credo.ge.credoapp.models.analysis.ReadyProduct;
import credo.ge.credoapp.models.analysis.TourismProductType;
import credo.ge.credoapp.models.analysis.UrbaProductType;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */


@ParserClassAnnotation(cols = {"მთავარი","შემოსავალები","ხარჯები","ბალანსი"})
public class Loan extends SugarRecord<Loan> {
    public String getName() {
        return person!=null?(person.fullName()+(product!=null?"":"")):"დაუსრულებელი სესხი";
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

    //@LabelFieldTypeViewAnotaion(label = "ანალიტიკა",position = 15,page = 1)
    public String label0;


    @ObjectsListFieldTypeViewAnottion(name = "აგრო პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 16,page = 1)
    public ArrayList<AgroProductType> agroProductTypes;
    @ObjectsListFieldTypeViewAnottion(name = "ურბანული პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 17,page = 1)
    public ArrayList<UrbaProductType> urbaProductTypes;
    @ObjectsListFieldTypeViewAnottion(name = "ტურისტული პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 18,page = 1)
    public ArrayList<TourismProductType> tourismProductTypes;
    @ObjectsListFieldTypeViewAnottion(name = "სხვა შემოსავლები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 19,page = 1)
    public ArrayList<OtherIncomeType> otherIncomeTypes;
    @ObjectsListFieldTypeViewAnottion(name = "ბიზნეს ხარჯები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 20,page = 2)
    public ArrayList<BusinesExpanse> businesExpanses;
    @ObjectsListFieldTypeViewAnottion(name = "სხვა ხარჯები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 21,page = 2)
    public ArrayList<OtherExpanse> otherExpanses;
    @ObjectsListFieldTypeViewAnottion(name = "ოჯახის ხარჯები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 22,page = 2)
    public ArrayList<FamilyExpanse> familyExpanses;
    @TextFieldTypeViewAnotation(name = "ოჯახის წევრების რაოდენობა", defaultValue = "1",type = "int",page = 2,position = 23)
    public int familyQuantity;

    @ObjectFieldTypeViewAnotation(name = "დასახლების ტიპი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 24,
            page = 2,
            filterWith ="1087")
    public Dictionary expanseType;


    @DataGroupContainerTypeViewAnotation(name = "ბიზნეს ბალანსი",page = 3,position = 25)
    public BusinessBalance businessBalance = new BusinessBalance();
    @DataGroupContainerTypeViewAnotation(name = "პირადი ბალანსი",page = 3,position = 26)
    public PersonalBalance personalBalance = new PersonalBalance();




    public static List<Loan> getData() {
        return Loan.listAll(Loan.class);
    }

    public static Loan getById(long id) {
        return Loan.findById(Loan.class, id);
    }

    public Loan() {
        try{
            businessBalance.save();
            personalBalance.save();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Loan(boolean save){

    }

    @Override
    public void save() {
        super.save();
    }
}
