package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.FamilyPerson;
import credo.ge.credoapp.models.Person;

/**
 * Created by kaxge on 6/8/2017.
 */

public class ObligationsToReceive extends SugarRecord<ObligationsToReceive> {
    @TextFieldTypeViewAnotation(name = "კომენტარი", defaultValue = "",type = "text", position = 1)
    public String comment="";
    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 2)
    public int sum=0;
    @TextFieldTypeViewAnotation(name = "დამ. ინფორმაცია", defaultValue = "",type = "text", position = 3)
    public String otherInfo="";

    public static List<FamilyPerson> findbybusinessbalance(long id) {
        return FamilyPerson.find(FamilyPerson.class, "businessbalance = ?", id + "");
    }
    public static List<ObligationsToReceive> getData() {
        return ObligationsToReceive.listAll(ObligationsToReceive.class);
    }
    public static ObligationsToReceive getById(long id) {
        return ObligationsToReceive.findById(ObligationsToReceive.class, id);
    }

    public BusinessBalance businessbalance;
}
