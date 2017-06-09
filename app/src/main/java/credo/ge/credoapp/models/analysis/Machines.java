package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by kaxge on 6/9/2017.
 */

public class Machines extends SugarRecord<Machines> {
    @TextFieldTypeViewAnotation(name = "კომენტარი", defaultValue = "",type = "text", position = 1)
    public String comment="";
    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 2)
    public int sum=0;
    @TextFieldTypeViewAnotation(name = "დამ. ინფორმაცია", defaultValue = "",type = "text", position = 3)
    public String otherInfo="";

    public static List<Machines> findbybusinessbalance(long id) {
        return Machines.find(Machines.class, "businessbalance = ?", id + "");
    }
    public static List<Machines> getData() {
        return Machines.listAll(Machines.class);
    }
    public static Machines getById(long id) {
        return Machines.findById(Machines.class, id);
    }

    public BusinessBalance businessbalance;
}
