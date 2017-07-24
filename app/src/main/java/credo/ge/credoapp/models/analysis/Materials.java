package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by kaxge on 6/9/2017.
 */

public class Materials extends SugarRecord{
    @TextFieldTypeViewAnotation(name = "კომენტარი",hint = "კომენტარი", defaultValue = "",type = "text", position = 1)
    public String comment="";
    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 2)
    public int sum=0;
    @TextFieldTypeViewAnotation(name = "დამ. ინფორმაცია", defaultValue = "",type = "text", position = 3)
    public String otherInfo="";

    public static List<Materials> findbybusinessbalance(long id) {
        return Materials.find(Materials.class, "businessbalance = ?", id + "");
    }
    public static List<Materials> getData() {
        return Materials.listAll(Materials.class);
    }
    public static Materials getById(long id) {
        return Materials.findById(Materials.class, id);
    }

    public BusinessBalance businessbalance;
}
