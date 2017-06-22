package credo.ge.credoapp.models;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import credo.ge.credoapp.StaticData;
import credo.ge.credoapp.anotations.ButtonFieldTypeViewAnnotation;
import credo.ge.credoapp.anotations.DataGroupContainerTypeViewAnotation;
import credo.ge.credoapp.anotations.DataGroupFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectsListFieldTypeViewAnottion;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.OnlineDataModels.SyncLoanResult;
import credo.ge.credoapp.models.analysis.AgroProductType;
import credo.ge.credoapp.models.analysis.Balance;
import credo.ge.credoapp.models.analysis.BusinesExpanse;
import credo.ge.credoapp.models.analysis.BusinessBalance;
import credo.ge.credoapp.models.analysis.ExpansesListItem;
import credo.ge.credoapp.models.analysis.FamilyExpanse;
import credo.ge.credoapp.models.analysis.OtherExpanse;
import credo.ge.credoapp.models.analysis.OtherIncomeType;
import credo.ge.credoapp.models.analysis.PersonalBalance;
import credo.ge.credoapp.models.analysis.ReadyProduct;
import credo.ge.credoapp.models.analysis.TourismProductType;
import credo.ge.credoapp.models.analysis.UrbaProductType;
import credo.ge.credoapp.online.OnlineData;
import rx.functions.Action1;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */


@ParserClassAnnotation(cols = {"მთავარი", "შემოსავალები", "ხარჯები", "ბალანსი"})
public class Loan extends SugarRecord<Loan> {
    public String getName() {
        return person != null ? (person.fullName() + (product != null ? "" : "")) : "დაუსრულებელი სესხი";
    }


    @ObjectFieldTypeViewAnotation(name = "პიროვნება",
            displayField = "fullName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 0)
    public Person person;


    @ObjectFieldTypeViewAnotation(name = "ფილიალი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 1)
    public Branch branch;


    @ObjectFieldTypeViewAnotation(name = "ოფიცერი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 2)
    public LoanOficer loanOficer;
    @ObjectFieldTypeViewAnotation(name = "პროდუქტი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 3)
    public Product product;
    @ObjectFieldTypeViewAnotation(name = "სოფელი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 4)
    public Vilage vilage;
    @ObjectFieldTypeViewAnotation(name = "სოფლის კონსული", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 5)
    public VilageCounsel vilageCounsel;
    @ObjectFieldTypeViewAnotation(name = "მიზნობრიობა", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 6, filterWith = "16")
    public Dictionary purposeType;
    @ObjectFieldTypeViewAnotation(name = "მიზნობრიობა", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 7)
    public Purpose purpose;
    @TextFieldTypeViewAnotation(name = "გამოცდილება", defaultValue = "1", type = "int", position = 8)
    public int gamocdileba;
    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1", type = "int", position = 9)
    public int loanSum;
    @ObjectFieldTypeViewAnotation(name = "ვალუტა", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 10, filterWith = "16")
    public Dictionary currency;

    @TextFieldTypeViewAnotation(name = "სესხის ვადა", defaultValue = "1", type = "int", position = 11)
    public int loanDuration;

    @TextFieldTypeViewAnotation(name = "კომენტარი", defaultValue = "", type = "text", position = 12)
    public String comment;



    public boolean sent = false;

    public String status = "დარეგისტრირებული";

    @TextFieldTypeViewAnotation(name = "ცხოველების რაოდენობა", defaultValue = "1", type = "int", position = 13)
    public int numberOfAnimals;
    @TextFieldTypeViewAnotation(name = "მიწა (ჰა)", defaultValue = "1", type = "int", position = 14)
    public int mitsa;


    @ObjectsListFieldTypeViewAnottion(name = "ხარჯების ჩამონათვალი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 15)
    public List<ExpansesListItem> expansesListItems;
    @ObjectsListFieldTypeViewAnottion(name = "აგრო პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 16, page = 1)
    public List<AgroProductType> agroProductTypes;
    @ObjectsListFieldTypeViewAnottion(name = "ურბანული პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 17, page = 1)
    public List<UrbaProductType> urbaProductTypes;
    @ObjectsListFieldTypeViewAnottion(name = "ტურისტული პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 18, page = 1)
    public List<TourismProductType> tourismProductTypes;
    @ObjectsListFieldTypeViewAnottion(name = "სხვა შემოსავლები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 19, page = 1)
    public List<OtherIncomeType> otherIncomeTypes;
    @ObjectsListFieldTypeViewAnottion(name = "ბიზნეს ხარჯები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 20, page = 2)
    public List<BusinesExpanse> businesExpanses;
    @ObjectsListFieldTypeViewAnottion(name = "სხვა ხარჯები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 21, page = 2)
    public List<OtherExpanse> otherExpanses;
    @ObjectsListFieldTypeViewAnottion(name = "ოჯახის ხარჯები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 22, page = 2)
    public List<FamilyExpanse> familyExpanses;
    @TextFieldTypeViewAnotation(name = "ოჯახის წევრების რაოდენობა", defaultValue = "1", type = "int", page = 2, position = 23)
    public int familyQuantity;
    @ObjectFieldTypeViewAnotation(name = "დასახლების ტიპი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 24,
            page = 2,
            filterWith = "1087")
    public Dictionary livigType;
    @DataGroupContainerTypeViewAnotation(name = "ბიზნეს ბალანსი", page = 3, position = 25)
    public BusinessBalance businessBalance = new BusinessBalance();
    @DataGroupContainerTypeViewAnotation(name = "პირადი ბალანსი", page = 3, position = 26)
    public PersonalBalance personalBalance = new PersonalBalance();

    public Loan getThis() {
        return this;
    }

    @ButtonFieldTypeViewAnnotation(name = "გაგზავნა", page = 3, position = 27)
    public View.OnClickListener sendClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(v, "clicked", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();


            agroProductTypes = AgroProductType.findbyloan(id);
            urbaProductTypes = UrbaProductType.findbyloan(id);
            tourismProductTypes = TourismProductType.findbyloan(id);
            otherIncomeTypes = OtherIncomeType.findbyloan(id);
            businesExpanses = BusinesExpanse.findbyloan(id);
            otherExpanses = OtherExpanse.findbyloan(id);
            familyExpanses = FamilyExpanse.findbyloan(id);
            expansesListItems = ExpansesListItem.findbyloan(id);
            person.family = FamilyPerson.findbyperson(person.getId());
            businessBalance.initData();
            personalBalance.initData();

            Gson g = new Gson();

            JsonObject jsonObject = g.toJsonTree(getThis()).getAsJsonObject();

            Log.d("kaxa", jsonObject.toString());


            OnlineData.INSTANCE.syncLoan(getThis(), new Action1<SyncLoanResult>() {
                @Override
                public void call(SyncLoanResult syncLoanResult) {
                    SyncLoanResult k = syncLoanResult;
                    boolean f = true;
                }
            });


        }
    };


    public static List<Loan> getData() {
        return Loan.listAll(Loan.class);
    }

    public static Loan getById(long id) {
        return Loan.findById(Loan.class, id);
    }

    public Loan() {
        try {
            businessBalance.save();
            personalBalance.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Loan(boolean save) {

    }

    @Override
    public void save() {
        super.save();
    }


    public long getBranchId() {
        return this.branch.serverId;
    }

    public long getOfficerId() {
        return this.loanOficer.serverId;
    }

    public long getVillage() {
        return this.vilage.serverId;
    }

    public long getConsul() {
        return this.vilageCounsel.serverId;
    }

    public long getProductId() {
        return this.product.serverId;
    }

    public long getPurposeId() {
        return this.purpose.serverId;
    }

    public int getExperience() {
        return this.gamocdileba;
    }

    public int getAmount() {
        return this.loanSum;
    }

    public String getComment() {
        return this.comment;
    }

    public long getCurrencyId() {
        return this.currency.serverId;
    }

    public int getPeriod() {
        return this.loanDuration;
    }

    public int getLand() {
        return this.mitsa;
    }

    public int getAnimal() {
        return this.numberOfAnimals;
    }

    public int getLivingTypeID() {
        return this.livigType.serverId;
    }

    public int getFamilyPersonsNum() {
        return this.familyQuantity;
    }


    public List<AgroProductType> getAgroProductTypes() {
        return AgroProductType.findbyloan(this.id);
    }

    public void setAgroProductTypes(ArrayList<AgroProductType> agroProductTypes) {
        this.agroProductTypes = agroProductTypes;
    }

    public List<UrbaProductType> getUrbaProductTypes() {
        return UrbaProductType.findbyloan(id);
    }

    public void setUrbaProductTypes(ArrayList<UrbaProductType> urbaProductTypes) {
        this.urbaProductTypes = urbaProductTypes;
    }

    public List<TourismProductType> getTourismProductTypes() {
        return TourismProductType.findbyloan(this.id);
    }

    public void setTourismProductTypes(ArrayList<TourismProductType> tourismProductTypes) {
        this.tourismProductTypes = tourismProductTypes;
    }

    public List<OtherIncomeType> getOtherIncomeTypes() {
        return OtherIncomeType.findbyloan(this.id);
    }

    public void setOtherIncomeTypes(ArrayList<OtherIncomeType> otherIncomeTypes) {
        this.otherIncomeTypes = otherIncomeTypes;
    }

    public List<BusinesExpanse> getBusinesExpanses() {
        return BusinesExpanse.findbyloan(this.id);
    }

    public void setBusinesExpanses(ArrayList<BusinesExpanse> businesExpanses) {
        this.businesExpanses = businesExpanses;
    }

    public List<OtherExpanse> getOtherExpanses() {
        return OtherExpanse.findbyloan(this.id);
    }

    public void setOtherExpanses(ArrayList<OtherExpanse> otherExpanses) {
        this.otherExpanses = otherExpanses;
    }

    public List<FamilyExpanse> getFamilyExpanses() {
        return FamilyExpanse.findbyloan(this.id);
    }

    public void setFamilyExpanses(ArrayList<FamilyExpanse> familyExpanses) {
        this.familyExpanses = familyExpanses;
    }
}
