package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.Cash;

/**
 * Created by kaxge on 6/9/2017.
 */

public class Cattle extends SugarRecord {
    @TextFieldTypeViewAnotation(name = "კომენტარი", hint = "კომენტარი",defaultValue = "",type = "text", position = 1)
    public String comment="";
    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 2)
    public int sum=0;
    @TextFieldTypeViewAnotation(name = "დამ. ინფორმაცია", defaultValue = "",type = "number", position = 3)
    public String otherInfo="";

    public static List<Cattle> findbybusinessbalance(long id) {
        return Cattle.find(Cattle.class, "businessbalance = ?", id + "");
    }
    public static List<Cattle> getData() {
        return Cattle.listAll(Cattle.class);
    }
    public static Cattle getById(long id) {
        return Cattle.findById(Cattle.class, id);
    }

    public BusinessBalance businessbalance;
}
