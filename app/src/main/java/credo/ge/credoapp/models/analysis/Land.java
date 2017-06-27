package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.Dictionary;

/**
 * Created by kaxge on 6/9/2017.
 */

public class Land extends SugarRecord {
    @TextFieldTypeViewAnotation(name = "კომენტარი", defaultValue = "",type = "text", position = 1)
    public String comment="";
    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 2)
    public int sum=0;
    @ObjectFieldTypeViewAnotation(name = "ტიპი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 3,
            filterWith ="39")
    public Dictionary landType;

    public static List<Land> findbybusinessbalance(long id) {
        return Land.find(Land.class, "businessbalance = ?", id + "");
    }
    public static List<Land> getData() {
        return Land.listAll(Land.class);
    }
    public static Land getById(long id) {
        return Land.findById(Land.class, id);
    }

    public BusinessBalance businessbalance;
}
