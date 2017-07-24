package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by kaxge on 6/9/2017.
 */

public class ReadyProduct extends SugarRecord {
    @TextFieldTypeViewAnotation(name = "კომენტარი",hint = "კომენტარი", defaultValue = "",type = "text", position = 1)
    public String comment="";
    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 2)
    public int sum=0;
    @TextFieldTypeViewAnotation(name = "დამ. ინფორმაცია", defaultValue = "",type = "text", position = 3)
    public String otherInfo="";

    public static List<ReadyProduct> findbybusinessbalance(long id) {
        return ReadyProduct.find(ReadyProduct.class, "businessbalance = ?", id + "");
    }
    public static List<ReadyProduct> getData() {
        return ReadyProduct.listAll(ReadyProduct.class);
    }
    public static ReadyProduct getById(long id) {
        return ReadyProduct.findById(ReadyProduct.class, id);
    }

    public BusinessBalance businessbalance;
}
