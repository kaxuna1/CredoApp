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
 * Created by kaxge on 6/14/2017.
 */
@ParserClassAnnotation
public class ExpansesListItem extends SugarRecord<ExpansesListItem> {



    public static ExpansesListItem getById(long id){
        return ExpansesListItem.findById(ExpansesListItem.class,id);
    }

    @ObjectFieldTypeViewAnotation(name = "ხარჯის ტიპი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 2,
            filterWith ="43")
    public Dictionary expanseType;

    @TextFieldTypeViewAnotation(name = "კომენტარი", defaultValue = "1",type = "int", position = 3)
    public int comment;





    @LabelFieldTypeViewAnotaion(label = "წლიური ხარჯი",position = 6)
    public String label1;


    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 8)
    public int sum;


    public static List<ExpansesListItem> findbyloan(long id) {
        return ExpansesListItem.find(ExpansesListItem.class, "loan = ?", id + "");
    }


    public String getName() {
        return expanseType.name +" "+sum ;
    }
    public Loan loan;
}
