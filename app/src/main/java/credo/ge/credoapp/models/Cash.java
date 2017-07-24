package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by kaxge on 6/5/2017.
 */

@ParserClassAnnotation
public class Cash extends SugarRecord {
    @TextFieldTypeViewAnotation(name = "კომენტარი",hint = "კომენტარი", defaultValue = "",type = "text", position = 1)
    public String comment="";
    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 2)
    public int sum=0;
    @TextFieldTypeViewAnotation(name = "დამ. ინფორმაცია", defaultValue = "",type = "text", position = 3)
    public String otherInfo="";
}
