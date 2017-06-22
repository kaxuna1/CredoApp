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
 * Created by kaxge on 5/23/2017.
 */


@ParserClassAnnotation
public class OtherIncomeType extends SugarRecord<OtherIncomeType> {
    @LabelFieldTypeViewAnotaion(label = "სხვა შემოსავალი",position = 1)
    public String label0;

    public static OtherIncomeType getById(long id){
        return OtherIncomeType.findById(OtherIncomeType.class,id);
    }

    @ObjectFieldTypeViewAnotation(name = "შემოსავლის ტიპი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 2,
            filterWith ="41")
    public Dictionary incomeType;

    @TextFieldTypeViewAnotation(name = "კომენტარი", defaultValue = "1",type = "text", position = 3)
    public String comment;





    @LabelFieldTypeViewAnotaion(label = "წლიური შემოსავალი",position = 6)
    public String label1;


    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 8)
    public int sum;


    public static List<OtherIncomeType> findbyloan(long id) {
        return OtherIncomeType.find(OtherIncomeType.class, "loan = ?", id + "");
    }


    public String getName() {
        return incomeType.name +" "+sum ;
    }
    public Loan loan;
}
