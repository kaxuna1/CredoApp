package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.Dictionary;

/**
 * Created by kaxge on 6/9/2017.
 */

public class LongObligation extends SugarRecord {
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

    public static List<LongObligation> findbybusinessbalance(long id) {
        return LongObligation.find(LongObligation.class, "businessbalance = ?", id + "");
    }
    public static List<LongObligation> getData() {
        return LongObligation.listAll(LongObligation.class);
    }
    public static LongObligation getById(long id) {
        return LongObligation.findById(LongObligation.class, id);
    }

    public BusinessBalance businessbalance;
    public PersonalBalance personalbalance;
    public static List<LongObligation> findbypersonalbalance(long id) {
        return LongObligation.find(LongObligation.class, "personalbalance = ?", id + "");
    }
}
