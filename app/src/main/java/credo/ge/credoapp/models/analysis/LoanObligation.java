package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.Dictionary;

/**
 * Created by kaxge on 6/9/2017.
 */

public class LoanObligation extends SugarRecord {
    @ObjectFieldTypeViewAnotation(name = "ბანკი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 0,
            filterWith ="10992")
    public Dictionary bank;
    @TextFieldTypeViewAnotation(name = "კომენტარი", defaultValue = "",type = "text", position = 1)
    public String comment="";
    @TextFieldTypeViewAnotation(name = "შენატანი", defaultValue = "1",type = "int", position = 2)
    public int sum=0;
    @TextFieldTypeViewAnotation(name = "ძირი თანხა", defaultValue = "1",type = "int", position = 3)
    public int sumToPay=0;
    @ObjectFieldTypeViewAnotation(name = "ტიპი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 4,
            filterWith ="40")
    public Dictionary type;

    public static List<LoanObligation> findbybusinessbalance(long id) {
        return LoanObligation.find(LoanObligation.class, "businessbalance = ?", id + "");
    }
    public static List<LoanObligation> getData() {
        return LoanObligation.listAll(LoanObligation.class);
    }
    public static LoanObligation getById(long id) {
        return LoanObligation.findById(LoanObligation.class, id);
    }

    public BusinessBalance businessbalance;
    public PersonalBalance personalbalance;
    public static List<LoanObligation> findbypersonalbalance(long id) {
        return LoanObligation.find(LoanObligation.class, "personalbalance = ?", id + "");
    }
}
