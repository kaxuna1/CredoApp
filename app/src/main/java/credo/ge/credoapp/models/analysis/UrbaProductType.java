package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.LabelFieldTypeViewAnotaion;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.Dictionary;
import credo.ge.credoapp.models.Loan;

/**
 * Created by kaxge on 5/22/2017.
 */

public class UrbaProductType extends SugarRecord<UrbaProductType> {

    @LabelFieldTypeViewAnotaion(label = "ურბანული პროდუქტი",position = 1)
    public String label0;


    @ObjectFieldTypeViewAnotation(name = "სერვისი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 2,
            filterWith ="33")
    public Dictionary service;
    @ObjectFieldTypeViewAnotation(name = "პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 3,
            filterWith ="67")
    public Dictionary product;

    @TextFieldTypeViewAnotation(name = "გამოცდილება", defaultValue = "1",type = "text", position = 5)
    public String comment;

    public String getName() {
        return product.name + " " + sum;
    }




    @LabelFieldTypeViewAnotaion(label = "წლიური შემოსავალი",position = 5)
    public String label1;

    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 6)
    public int sum;


    public static List<UrbaProductType> findbyloan(long id) {
        return UrbaProductType.find(UrbaProductType.class, "loan = ?", id + "");
    }

    public Loan loan;
}
