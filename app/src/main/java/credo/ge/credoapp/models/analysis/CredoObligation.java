package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.Dictionary;
import credo.ge.credoapp.models.Product;

/**
 * Created by kaxge on 6/9/2017.
 */

public class CredoObligation extends SugarRecord {
    @ObjectFieldTypeViewAnotation(name = "პროდუქტი", displayField = "getName", isMethod = true, type = "comboBox", sqlData = true, canAddToDb = false, position = 1)
    public Product product;
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

    public static List<CredoObligation> findbybusinessbalance(long id) {
        return CredoObligation.find(CredoObligation.class, "businessbalance = ?", id + "");
    }
    public static List<CredoObligation> getData() {
        return CredoObligation.listAll(CredoObligation.class);
    }
    public static CredoObligation getById(long id) {
        return CredoObligation.findById(CredoObligation.class, id);
    }

    public BusinessBalance businessbalance;
    public PersonalBalance personalbalance;
    public static List<CredoObligation> findbypersonalbalance(long id) {
        return CredoObligation.find(CredoObligation.class, "personalbalance = ?", id + "");
    }
}
