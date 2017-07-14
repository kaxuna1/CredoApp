package credo.ge.credoapp.models;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import credo.ge.credoapp.anotations.ButtonFieldTypeViewAnnotation;
import credo.ge.credoapp.anotations.DateFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectsListFieldTypeViewAnottion;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.OnlineDataModels.SyncLoanResult;
import credo.ge.credoapp.models.analysis.AgroProductType;
import credo.ge.credoapp.models.analysis.BusinesExpanse;
import credo.ge.credoapp.models.analysis.ExpansesListItem;
import credo.ge.credoapp.models.analysis.FamilyExpanse;
import credo.ge.credoapp.models.analysis.OtherExpanse;
import credo.ge.credoapp.models.analysis.OtherIncomeType;
import credo.ge.credoapp.models.analysis.TourismProductType;
import credo.ge.credoapp.models.analysis.UrbaProductType;
import credo.ge.credoapp.online.OnlineData;
import credo.ge.credoapp.sent_loan_page;
import rx.functions.Action1;

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */
@ParserClassAnnotation(cols = {"კლიენტი", "ოჯახის წევრები"})
public class Person extends SugarRecord {
    @TextFieldTypeViewAnotation(name = "პირადი ნომერი", required = true, requiredForSave = true, mask = "###########",defaultValue = "", type = "text", length = 11,position = 1)
    public String personalNumber = "";

    @TextFieldTypeViewAnotation(name = "სახელი", hint = "სახელი",required = true, requiredForSave = true, defaultValue = "", type = "text", position = 2)
    public String name = "";

    @TextFieldTypeViewAnotation(name = "გვარი", hint = "გვარი", required = true, requiredForSave = true, defaultValue = "", type = "text", position = 3)
    public String surname = "";

    @DateFieldTypeViewAnotation(name = "დაბადების თარიღი", requiredForSave = true, position = 4)
    public Date birthDate = new Date();


    @TextFieldTypeViewAnotation(name = "ფაქტობრივი მისამართი",hint = "მისამართი", requiredForSave = true, defaultValue = "", type = "text", position = 5)
    public String address = "";

    @ObjectFieldTypeViewAnotation(name = "ტიპი", displayField = "getName", requiredForSave = true, isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 6, filterWith = "13")
    public Dictionary personType;

    @TextFieldTypeViewAnotation(name = "მობილური",mask = "#########",defaultValue = "", requiredForSave = true, type = "number", length = 9, position = 7)
    public String mobile = "";

    @TextFieldTypeViewAnotation(name = "ტელეფონი", defaultValue = "", type = "number", position = 8)
    public String phone = "";
    @TextFieldTypeViewAnotation(name = "დამოკიდებულ პირთა რაოდენობა", requiredForSave = true, defaultValue = "", type = "int", position = 9)
    public int connectedPersonsNumber = 0;

    public int serverId;

    @ObjectFieldTypeViewAnotation(name = "სქესი", displayField = "getName", requiredForSave = true, isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 10, filterWith = "3")
    public Dictionary sexType;

    @ObjectFieldTypeViewAnotation(name = "ოჯახური მდგომარეობა",
            displayField = "getName", isMethod = true,
            type = "comboBox", sqlData = true,
            requiredForSave = true,
            canAddToDb = false, position = 11,
            filterWith = "100")
    public Dictionary ojaxuriMdgomareoba;


    @ObjectFieldTypeViewAnotation(name = "სექტორი", requiredForSave = true, displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 12, filterWith = "101")
    public Dictionary sector;

    @ObjectFieldTypeViewAnotation(name = "ინდუსტრია",filterWithField = "sector:serverId:sectorId",requiredForSave = true, displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false,
            position = 13)
    public Industry industry;

    public int ready = 0;

    public Person getThis() {
        return this;
    }

    @ButtonFieldTypeViewAnnotation(name = "CSS-ში რეგისტრაცია", position = 14)
    public View.OnClickListener sendClick = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (isValid(v)) {
                Loan l = new Loan();
                l.person = Person.getById(getId());
                final ACProgressFlower dialog = new ACProgressFlower.Builder(v.getContext())
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("რეგისტრაცია")
                        .fadeColor(Color.DKGRAY).build();
                dialog.show();
                OnlineData.INSTANCE.syncLoan(l, new Action1<SyncLoanResult>() {
                    @Override
                    public void call(SyncLoanResult syncLoanResult) {
                        if (syncLoanResult != null) {

                            ready = 1;
                            getThis().save();
                            dialog.hide();
                            Snackbar.make(v, "მომხმარებელი დამატებულია CSS-ში!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            //
                            dialog.hide();
                            Snackbar.make(v, "მოხდა შეცდომა კლიენტის რეგისტრაციის დროს!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }
                });

            } else {
                Snackbar.make(v, "შეავსეთ სავალდებულო ველები", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    };

    public boolean isValid(View v) {
        boolean valid = true;
        if (personalNumber == "") {
            valid = false;
        }
        if (personalNumber.length()<11){
            valid = false;
            Snackbar.make(v, "პირადი ნომერი 11 სიმბოლოზე ნაკლებია!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        if (name == "") {
            valid = false;
        }
        if (surname == "") {
            valid = false;
        }
        if (birthDate == null) {
            valid = false;
        }
        if (address == "") {
            valid = false;
        }
        if (personType == null) {
            valid = false;
        }
        if (mobile == "") {
            valid = false;
        }
        if (connectedPersonsNumber == 0) {
            valid = false;
        }
        if (sexType == null) {
            valid = false;
        }
        if (ojaxuriMdgomareoba == null) {
            valid = false;
        }
        if (sector == null) {
            valid = false;
        }
        if (industry == null) {
            valid = false;
        }
        return valid;
    }


    @ObjectsListFieldTypeViewAnottion(name = "ოჯახის წევრები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false,
            joinField = "person", position = 15, page = 1)
    public List<FamilyPerson> family;

    public String fullName() {
        return (name == "" ? "სახელი" : name) + " " + (surname == "" ? "გვარი" : surname);
    }

    public static List<Person> getData() {
        return Person.listAll(Person.class);
    }

    public static List<Person> getData(String type) {
        return Person.find(Person.class, "ready = ?", type);
    }

    public static Person getById(long id) {
        return Person.findById(Person.class, id);
    }


    public Person() {
        family = new ArrayList<>();
    }

    public List<FamilyPerson> getFamily() {
        return FamilyPerson.findbyperson(this.getId());
    }

    public void setFamily(ArrayList<FamilyPerson> family) {
        this.family = family;
    }

    @Override
    public String toString() {
        return personalNumber == null ? "" : personalNumber;
    }
}
