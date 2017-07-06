package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.LabelFieldTypeViewAnotaion;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.Dictionary;
import credo.ge.credoapp.models.ExpanseType;
import credo.ge.credoapp.models.Loan;

/**
 * Created by kaxge on 6/14/2017.
 */
@ParserClassAnnotation
public class ExpansesListItem extends SugarRecord {



    public static ExpansesListItem getById(long id){
        return ExpansesListItem.findById(ExpansesListItem.class,id);
    }

    @ObjectFieldTypeViewAnotation(name = "ხარჯის ტიპი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",requiredForSave = true,
            sqlData = true,
            canAddToDb = false, position = 2)
    public ExpanseType expanseType;

    @TextFieldTypeViewAnotation(name = "კომენტარი", defaultValue = "1",type = "int", position = 3)
    public int comment;






    public static List<ExpansesListItem> findbyloan(long id) {
        return ExpansesListItem.find(ExpansesListItem.class, "loan = ?", id + "");
    }


    public String getName() {
        return expanseType.name +" "+expanseType.amount+" "+expanseType.cur ;
    }
    public Loan loan;
}
