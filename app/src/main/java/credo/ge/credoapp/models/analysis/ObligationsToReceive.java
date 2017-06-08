package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

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


    public BusinessBalance businessBalance;
}
