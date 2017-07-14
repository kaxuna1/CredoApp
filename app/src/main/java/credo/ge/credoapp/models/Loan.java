package credo.ge.credoapp.models;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orm.SugarRecord;
import com.sromku.simple.storage.InternalStorage;
import com.sromku.simple.storage.SimpleStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import credo.ge.credoapp.R;
import credo.ge.credoapp.StaticData;
import credo.ge.credoapp.StaticJava;
import credo.ge.credoapp.anotations.ButtonFieldTypeViewAnnotation;
import credo.ge.credoapp.anotations.DataGroupContainerTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectsListFieldTypeViewAnottion;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.OnlineDataModels.SyncLoanResult;
import credo.ge.credoapp.models.analysis.AgroProductType;
import credo.ge.credoapp.models.analysis.BusinesExpanse;
import credo.ge.credoapp.models.analysis.BusinessBalance;
import credo.ge.credoapp.models.analysis.ExpansesListItem;
import credo.ge.credoapp.models.analysis.FamilyExpanse;
import credo.ge.credoapp.models.analysis.OtherExpanse;
import credo.ge.credoapp.models.analysis.OtherIncomeType;
import credo.ge.credoapp.models.analysis.PersonalBalance;
import credo.ge.credoapp.models.analysis.TourismProductType;
import credo.ge.credoapp.models.analysis.UrbaProductType;
import credo.ge.credoapp.online.OnlineData;
import credo.ge.credoapp.sent_loan_page;
import rx.functions.Action1;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */


@ParserClassAnnotation(cols = {"სესხის განაცხადი", "შემოსავალები", "ხარჯები", "ბალანსი"})
public class Loan extends SugarRecord {

    @ObjectFieldTypeViewAnotation(name = "პიროვნება",
            displayField = "fullName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            requiredForSave = true,
            canAddToDb = false, position = 0)
    public Person person;


    @ObjectFieldTypeViewAnotation(name = "ფილიალი",
            requiredForSave = true, displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 1)
    public Branch branch;


    @ObjectFieldTypeViewAnotation(name = "ოფიცერი",
            requiredForSave = true, displayField = "getName", filterWithField = "branch:serverId:branchId", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 2)
    public LoanOficer loanOficer;
    @ObjectFieldTypeViewAnotation(name = "პროდუქტი",
            requiredForSave = true, displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 3)
    public Product product;
    @ObjectFieldTypeViewAnotation(name = "სოფელი",
            requiredForSave = true, displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 4)
    public Vilage vilage;
    @ObjectFieldTypeViewAnotation(name = "სოფლის კონსული",
            requiredForSave = true, displayField = "getName", filterWithField = "vilage:serverId:villageId", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 5)
    public VilageCounsel vilageCounsel;
    @ObjectFieldTypeViewAnotation(name = "მიზნობრიობის ტიპი",
            requiredForSave = true, displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 6)
    public PurposeType purposeType;
    @ObjectFieldTypeViewAnotation(name = "მიზნობრიობა",
            requiredForSave = true,
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            filterWithField = "purposeType:type:type",
            position = 7)
    public Purpose purpose;
    @TextFieldTypeViewAnotation(name = "გამოცდილება",
            requiredForSave = true, defaultValue = "0", type = "int", position = 8, hint = "წელი")
    public int gamocdileba;
    @TextFieldTypeViewAnotation(name = "თანხა",
            requiredForSave = true, defaultValue = "0", type = "int", hint = "თანხა", position = 9)
    public int loanSum;
    @ObjectFieldTypeViewAnotation(name = "ვალუტა",
            requiredForSave = true, displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 10, filterWith = "16")
    public Dictionary currency;

    @TextFieldTypeViewAnotation(name = "სესხის ვადა",
            requiredForSave = true, hint = "თვე", defaultValue = "1", type = "int", position = 11)
    public int loanDuration;

    @TextFieldTypeViewAnotation(name = "კომენტარი", hint = "კომენტარი", defaultValue = "", type = "text", position = 12)
    public String comment;


    public Context context;

    public boolean sent = false;

    public String status = "m0";

    @TextFieldTypeViewAnotation(name = "ცხოველების რაოდენობა", defaultValue = "", visibilityPatern = {"product:A1,A2"}, type = "int", position = 13)
    public int numberOfAnimals = 0;
    @TextFieldTypeViewAnotation(name = "მიწა (ჰა)", mask = "#######", defaultValue = "", type = "number", visibilityPatern = {"product:A1,A2"}, position = 14)
    public String mitsa = "";


    @ObjectsListFieldTypeViewAnottion(name = "ხარჯების ჩამონათვალი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "loan", position = 15)
    public List<ExpansesListItem> expansesListItems;


    @ButtonFieldTypeViewAnnotation(name = "ანალიზის გარეშე გაგზავნა გაგზავნა", position = 28, icon = R.drawable.cloudup)
    public View.OnClickListener sendClick2 = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {


            if (StaticData.INSTANCE.isNetworkAvailable(v.getContext())) {
                if (isValidNoAnalitic(v.getContext(), v)) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                    builder1.setMessage("გსურთ გააგზავნოთ სესხი CSS-ში?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "კი",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogAlert, int id) {

                                    final ACProgressFlower dialog = new ACProgressFlower.Builder(v.getContext())
                                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                            .themeColor(Color.WHITE)
                                            .text("რეგისტრაცია")
                                            .fadeColor(Color.DKGRAY).build();
                                    dialog.show();

                                    final BusinessBalance businessBalanceLocal = businessBalance;
                                    final PersonalBalance personalBalanceLocal = personalBalance;

                                    agroProductTypes = new ArrayList<>();
                                    urbaProductTypes = new ArrayList<>();
                                    tourismProductTypes = new ArrayList<>();
                                    otherIncomeTypes = new ArrayList<>();
                                    businesExpanses = new ArrayList<>();
                                    otherExpanses = new ArrayList<>();
                                    familyExpanses = new ArrayList<>();
                                    expansesListItems = new ArrayList<>();
                                    person.family = new ArrayList<>();
                                    businessBalance = new BusinessBalance();
                                    personalBalance = new PersonalBalance();


                                    Gson g = new Gson();

                                    JsonObject jsonObject = g.toJsonTree(getThis()).getAsJsonObject();

                                    Log.d("LoanLog", jsonObject.toString());



                                    try {
                                        OnlineData.INSTANCE.syncLoan(getThis(), new Action1<SyncLoanResult>() {
                                            @Override
                                            public void call(SyncLoanResult syncLoanResult) {
                                                if (syncLoanResult != null) {
                                                    if (syncLoanResult.data != null) {
                                                        if (syncLoanResult.data.loanID > 0) {
                                                            Loan tempLoan = Loan.getById(getThis().getId());
                                                            tempLoan.serverId = syncLoanResult.data.loanID;
                                                            tempLoan.sent = true;
                                                            tempLoan.status = syncLoanResult.data.errorMessage;
                                                            tempLoan.save();
                                                            tempLoan.person.serverId = syncLoanResult.data.personID;
                                                            tempLoan.person.save();
                                                            dialog.hide();
                                                            Intent intent = new Intent(v.getContext(), sent_loan_page.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                            intent.putExtra("id", tempLoan.getId());
                                                            v.getContext().startActivity(intent);

                                                        }
                                                    } else {
                                                        dialog.hide();
                                                    }
                                                } else {
                                                    dialog.hide();
                                                    Snackbar.make(v, "სესხის რეგისტრაციის დროს მოხდა შეცდომა!", Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
                                                }

                                            }
                                        });
                                    } catch (Exception e) {
                                        dialog.hide();
                                    }


                                    dialogAlert.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "არა",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                } else {

                }
            } else {
                Snackbar.make(v, "სესხის რეგისტრაციისთვის საჭიროა ინტერნეტ კავშირი!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }


        }
    };


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

    public int serverId = 0;

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
    public int familyQuantity = 0;
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


    public int applicationStatusId=0;
    public float amount=0;
    public String currencyName="";
    public int period=0;
    public float intRate=0;
    public float effectivePercent=0;
    public float loanCommission=0;
    public float smsCommission=0;
    public float payment=0;
    public String statusChangeDate="";


    public Loan getThis() {
        return this;
    }

    public boolean isValidNoAnalitic(Context context, View view) {


        if (person == null) {
            Snackbar.make(view, "აირჩიეთ კლიენტი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (branch == null) {
            Snackbar.make(view, "აირჩიეთ ფილიალი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (loanOficer == null) {
            Snackbar.make(view, "აირჩიეთ ოფიცერი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (product == null) {
            Snackbar.make(view, "აირჩიეთ პროდუქტი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (vilage == null) {
            Snackbar.make(view, "აირჩიეთ სოფელი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (vilageCounsel == null) {
            Snackbar.make(view, "აირჩიეთ კონსული", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (purposeType == null) {
            Snackbar.make(view, "აირჩიეთ მიზნობრიობის ტიპი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (purpose == null) {
            Snackbar.make(view, "აირჩიეთ მიზნობრიობა", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (gamocdileba == 0) {
            Snackbar.make(view, "შეავსეთ გამოცდილება", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (loanSum == 0) {
            Snackbar.make(view, "შეავსეთ სესხის თანხა", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (loanDuration == 0) {
            Snackbar.make(view, "შეავსეთ სესხის ხანგრძლივობა", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        return true;
    }

    public boolean isValid(Context context, View view) {
        boolean valid = true;


        if (person == null) {
            Snackbar.make(view, "აირჩიეთ კლიენტი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (branch == null) {
            Snackbar.make(view, "აირჩიეთ ფილიალი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (loanOficer == null) {
            Snackbar.make(view, "აირჩიეთ ოფიცერი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (product == null) {
            Snackbar.make(view, "აირჩიეთ პროდუქტი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (vilage == null) {
            Snackbar.make(view, "აირჩიეთ სოფელი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (vilageCounsel == null) {
            Snackbar.make(view, "აირჩიეთ კონსული", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (purposeType == null) {
            Snackbar.make(view, "აირჩიეთ მიზნობრიობის ტიპი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (purpose == null) {
            Snackbar.make(view, "აირჩიეთ მიზნობრიობა", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (gamocdileba == 0) {
            Snackbar.make(view, "შეავსეთ გამოცდილება", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (loanSum == 0) {
            Snackbar.make(view, "შეავსეთ სესხის თანხა", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (loanDuration == 0) {
            Snackbar.make(view, "შეავსეთ სესხის ხანგრძლივობა", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
    /*    if (numberOfAnimals == 0) {
            Snackbar.make(view, "შეავსეთ ცხოველების რაოდენობა", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (mitsa == 0) {
            Snackbar.make(view, "შეავსეთ მიწა", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }*/
        if (familyQuantity == 0) {
            Snackbar.make(view, "ოჯახის წევრების რაოდენობა", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if (livigType == null) {
            Snackbar.make(view, "აირჩიეთ დასახლების ტიპი", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }
        if ((BusinesExpanse.findbyloan(getId()).size() + OtherExpanse.findbyloan(getId()).size() + FamilyExpanse.findbyloan(getId()).size()) == 0) {
            Snackbar.make(view, "ხარჯები არ გაქვთ შევსებული", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        if ((businessBalance.getCount() + personalBalance.getCount()) == 0) {
            Snackbar.make(view, "ბალანსი არ გაქვთ შევსებული", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        if (!businessBalance.isValid()) {
            Snackbar.make(view, "ბიზნეს ბალანსში შეავსეთ ყველა სავალდებულო ველი!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        if (!personalBalance.isValid()) {
            Snackbar.make(view, "პირად ბალანსში შეავსეთ ყველა სავალდებულო ველი!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }


        InternalStorage storage = SimpleStorage.getInternalStorage(context);
        storage.createDirectory("pdf");
        storage.createFile("pdf", "kaxa", "some content of the file");
        try {
            byte[] file = storage.readFile("pdf", person.personalNumber);
            PdfFile pdfFile = new PdfFile();

        } catch (Exception e) {
            Snackbar.make(view, "შეასრულეთ კლიენტის შემოწმება", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }


        return valid;
    }

    public String getStatus() {
        if (status != null) {
            switch (status) {
                case "m0":
                    return "დარეგისტრირებული";
                case "0":
                    return "დამუშავების პროცესში";
                case "1":
                    return "დამტკიცების პროცესში";
                case "2":
                    return "დამტკიცებული";
                case "3":
                    return "დაუარებული";
                case "4":
                    return "გაუქმებული";
                case "5":
                    return "განხილვის პროცესში";
                case "6":
                    return "გადამტკიცების პროცესში";
                case "7":
                    return "გაცემული";
                case "8":
                    return "განიხილება";
                case "9":
                    return "გადაგზავნილია რისკის ოფიცერთან";
                case "10":
                    return "დაბრუნებული ცვლილებით";
                case "11":
                    return "Back Office ში გადაგზავნილი";
                default:
                    return "გაურკვეველი";

            }
        } else {
            return "გაურკვეველი";
        }


    }


    @ButtonFieldTypeViewAnnotation(name = "გაგზავნა", page = 3, position = 27, icon = R.drawable.cloudup)
    public View.OnClickListener sendClick = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {


            if (StaticData.INSTANCE.isNetworkAvailable(v.getContext())) {
                final ACProgressFlower dialog3 = new ACProgressFlower.Builder(v.getContext())
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("დაიცადეთ")
                        .fadeColor(Color.DKGRAY).build();
                dialog3.show();
                agroProductTypes = AgroProductType.findbyloan(getId());
                urbaProductTypes = UrbaProductType.findbyloan(getId());
                tourismProductTypes = TourismProductType.findbyloan(getId());
                otherIncomeTypes = OtherIncomeType.findbyloan(getId());
                businesExpanses = BusinesExpanse.findbyloan(getId());
                otherExpanses = OtherExpanse.findbyloan(getId());
                familyExpanses = FamilyExpanse.findbyloan(getId());
                expansesListItems = ExpansesListItem.findbyloan(getId());
                person.family = FamilyPerson.findbyperson(person.getId());
                businessBalance.initData();
                personalBalance.initData();

                dialog3.hide();

                if (isValid(v.getContext(), v)) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                    builder1.setMessage("გსურთ გააგზავნოთ სესხი CSS-ში?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "კი",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogAlert, int id) {


                                    try{

                                        if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                            LocationManager manager = (LocationManager) v.getContext().getSystemService(Context.LOCATION_SERVICE);
                                            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                            getThis().x2 = location.getLongitude();
                                            getThis().y2 = location.getLatitude();
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }


                                    Gson g = new Gson();

                                    JsonObject jsonObject = g.toJsonTree(getThis()).getAsJsonObject();

                                    Log.d("kaxa", jsonObject.toString());


                                    final ACProgressFlower dialog = new ACProgressFlower.Builder(v.getContext())
                                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                            .themeColor(Color.WHITE)
                                            .text("რეგისტრაცია")
                                            .fadeColor(Color.DKGRAY).build();
                                    dialog.show();
                                    try {
                                        OnlineData.INSTANCE.syncLoan(getThis(), new Action1<SyncLoanResult>() {
                                            @Override
                                            public void call(SyncLoanResult syncLoanResult) {
                                                if (syncLoanResult != null) {
                                                    if (syncLoanResult.data != null) {
                                                        if (syncLoanResult.data.loanID > 0) {
                                                            getThis().serverId = syncLoanResult.data.loanID;
                                                            getThis().sent = true;
                                                            getThis().status = syncLoanResult.data.errorMessage;
                                                            getThis().save();
                                                            dialog.hide();
                                                            final ACProgressFlower dialog2 = new ACProgressFlower.Builder(v.getContext())
                                                                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                                                    .themeColor(Color.WHITE)
                                                                    .text("სქორინგი")
                                                                    .fadeColor(Color.DKGRAY).build();
                                                            dialog2.show();
                                                            OnlineData.INSTANCE.syncLoanScoring(getThis(), new Action1<SyncLoanResult>() {
                                                                @Override
                                                                public void call(SyncLoanResult syncLoanResult) {
                                                                    if (syncLoanResult != null) {
                                                                        dialog2.hide();

                                                                        OnlineData.INSTANCE.syncLoanStatus(getThis(), new Action1<SyncLoanResult>() {
                                                                            @Override
                                                                            public void call(SyncLoanResult syncLoanResult2) {
                                                                                if (syncLoanResult2 != null) {
                                                                                    getThis().status = syncLoanResult2.data.result.getApplicationStatusId() + "";
                                                                                    getThis().applicationStatusId = syncLoanResult2.data.result.getApplicationStatusId();
                                                                                    getThis().amount = syncLoanResult2.data.result.getAmount();
                                                                                    getThis().currencyName = syncLoanResult2.data.result.getCurrencyName();
                                                                                    getThis().period = syncLoanResult2.data.result.getPeriod();
                                                                                    getThis().intRate = syncLoanResult2.data.result.getIntRate();
                                                                                    getThis().effectivePercent = syncLoanResult2.data.result.getEffectivePercent();
                                                                                    getThis().loanCommission = syncLoanResult2.data.result.getLoanCommission();
                                                                                    getThis().smsCommission = syncLoanResult2.data.result.getSmsCommission();
                                                                                    getThis().payment = syncLoanResult2.data.result.getPayment();
                                                                                    getThis().statusChangeDate = syncLoanResult2.data.result.getStatusChangeDate();

                                                                                    getThis().save();
                                                                                    Intent intent = new Intent(v.getContext(), sent_loan_page.class);
                                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                                                    intent.putExtra("id", getThis().getId());
                                                                                    v.getContext().startActivity(intent);
                                                                                } else {
                                                                                    Snackbar.make(v, "სესხის სტატუსის განახლების დროს მოხდა შეცდომა! \n განაახლეთ სტატუსი მოთხოვნების გვერდიდან.", Snackbar.LENGTH_LONG)
                                                                                            .setAction("Action", null).show();
                                                                                }
                                                                            }
                                                                        });

                                                                    } else {
                                                                        dialog2.hide();
                                                                        Snackbar.make(v, "სესხის სქორინგის დროს მოხდა შეცდომა! \n განაახლეთ სტატუსი გაგზავნი მოთხოვნების გვერდიდან.", Snackbar.LENGTH_LONG)
                                                                                .setAction("Action", null).show();
                                                                    }


                                                                }
                                                            });

                                                        } else {
                                                            dialog.hide();
                                                        }
                                                    } else {
                                                        dialog.hide();
                                                    }
                                                } else {
                                                    dialog.hide();
                                                    Snackbar.make(v, "სესხის რეგისტრაციის დროს მოხდა შეცდომა!", Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
                                                }

                                            }
                                        });
                                    } catch (Exception e) {
                                        dialog.hide();
                                    }


                                    dialogAlert.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "არა",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                } else {

                }
            } else {
                Snackbar.make(v, "სესხის რეგისტრაციისთვის საჭიროა ინტერნეტ კავშირი!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }


        }
    };
    double x1 = 0;
    double x2 = 0;
    double y1 = 0;
    double y2 = 0;


    public static List<Loan> getData() {
        return Loan.listAll(Loan.class);
    }

    public static Loan getById(long id) {
        return Loan.findById(Loan.class, id);
    }

    public Loan() {
        try {
            x1 = StaticData.INSTANCE.getX();
            y1 = StaticData.INSTANCE.getY();
            businessBalance.save();
            personalBalance.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Loan(boolean save) {

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

    public String getLand() {
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
        return AgroProductType.findbyloan(this.getId());
    }

    public void setAgroProductTypes(ArrayList<AgroProductType> agroProductTypes) {
        this.agroProductTypes = agroProductTypes;
    }

    public List<UrbaProductType> getUrbaProductTypes() {
        return UrbaProductType.findbyloan(getId());
    }

    public void setUrbaProductTypes(ArrayList<UrbaProductType> urbaProductTypes) {
        this.urbaProductTypes = urbaProductTypes;
    }

    public List<TourismProductType> getTourismProductTypes() {
        return TourismProductType.findbyloan(this.getId());
    }

    public void setTourismProductTypes(ArrayList<TourismProductType> tourismProductTypes) {
        this.tourismProductTypes = tourismProductTypes;
    }

    public List<OtherIncomeType> getOtherIncomeTypes() {
        return OtherIncomeType.findbyloan(this.getId());
    }

    public void setOtherIncomeTypes(ArrayList<OtherIncomeType> otherIncomeTypes) {
        this.otherIncomeTypes = otherIncomeTypes;
    }

    public List<BusinesExpanse> getBusinesExpanses() {
        return BusinesExpanse.findbyloan(this.getId());
    }

    public void setBusinesExpanses(ArrayList<BusinesExpanse> businesExpanses) {
        this.businesExpanses = businesExpanses;
    }

    public List<OtherExpanse> getOtherExpanses() {
        return OtherExpanse.findbyloan(this.getId());
    }

    public void setOtherExpanses(ArrayList<OtherExpanse> otherExpanses) {
        this.otherExpanses = otherExpanses;
    }

    public List<FamilyExpanse> getFamilyExpanses() {
        return FamilyExpanse.findbyloan(this.getId());
    }

    public void setFamilyExpanses(ArrayList<FamilyExpanse> familyExpanses) {
        this.familyExpanses = familyExpanses;
    }

    public String getName() {
        return person.fullName() + " " + product.getName() + " " + loanSum + " " + currency.getName();
    }
}
