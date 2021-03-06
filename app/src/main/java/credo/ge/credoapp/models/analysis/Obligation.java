package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.Dictionary;

/**
 * Created by kaxge on 6/9/2017.
 */

public class Obligation extends SugarRecord {

    @ObjectFieldTypeViewAnotation(name = "ბანკი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            requiredForSave = true,
            canAddToDb = false, position = 0,
            filterWith ="10992")
    public Dictionary bank;
    @TextFieldTypeViewAnotation(name = "კომენტარი",hint = "კომენტარი",requiredForSave = true, defaultValue = "",type = "text", position = 1)
    public String comment="";
    @TextFieldTypeViewAnotation(name = "შენატანი",requiredForSave = true, defaultValue = "1",type = "int", position = 2)
    public int sum=0;
    @TextFieldTypeViewAnotation(name = "ძირი თანხა",requiredForSave = true, defaultValue = "1",type = "int", position = 3)
    public int sumToPay=0;
    @ObjectFieldTypeViewAnotation(name = "ტიპი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,requiredForSave = true,
            canAddToDb = false, position = 4,
            filterWith ="40")
    public Dictionary type;

    public static List<Obligation> findbybusinessbalance(long id) {
        return Obligation.find(Obligation.class, "businessbalance = ?", id + "");
    }
    public static List<Obligation> getData() {
        return Obligation.listAll(Obligation.class);
    }
    public static Obligation getById(long id) {
        return Obligation.findById(Obligation.class, id);
    }

    public BusinessBalance businessbalance;
    public PersonalBalance personalbalance;
    public static List<Obligation> findbypersonalbalance(long id) {
        return Obligation.find(Obligation.class, "personalbalance = ?", id + "");
    }
}
