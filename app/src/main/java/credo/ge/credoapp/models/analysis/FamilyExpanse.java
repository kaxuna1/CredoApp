package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.LabelFieldTypeViewAnotaion;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.Dictionary;
import credo.ge.credoapp.models.Loan;

/**
 * Created by kaxge on 5/24/2017.
 */
@ParserClassAnnotation
public class FamilyExpanse extends SugarRecord {
    @LabelFieldTypeViewAnotaion(label = "ხარჯები",position = 1)
    public String label0;


    @ObjectFieldTypeViewAnotation(name = "ოჯახის ხარჯები",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,requiredForSave = true,
            canAddToDb = false, position = 2,
            filterWith ="44")
    public Dictionary expanseType;

    @TextFieldTypeViewAnotation(name = "კომენტარი",hint = "კომენტარი", defaultValue = "",type = "text", position = 3)
    public String comment="";





    @LabelFieldTypeViewAnotaion(label = "წლიური ხარჯი",position = 6)
    public String label1;


    @TextFieldTypeViewAnotation(name = "თანხა",requiredForSave = true, defaultValue = "0",type = "int", position = 8)
    public int sum;


    public static List<FamilyExpanse> findbyloan(long id) {
        return FamilyExpanse.find(FamilyExpanse.class, "loan = ?", id + "");
    }

    public static FamilyExpanse getById(long id){
        return FamilyExpanse.findById(FamilyExpanse.class,id);
    }


    public String getName() {
        return expanseType.name +" "+sum ;
    }
    public Loan loan;
}
