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
 * Created by kaxge on 5/22/2017.
 */
@ParserClassAnnotation
public class UrbaProductType extends SugarRecord {

    @LabelFieldTypeViewAnotaion(label = "ურბანული პროდუქტი",position = 1)
    public String label0;


    public static UrbaProductType getById(long id){
        return UrbaProductType.findById(UrbaProductType.class,id);
    }

    @ObjectFieldTypeViewAnotation(name = "სერვისი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,requiredForSave = true,
            canAddToDb = false, position = 2,
            filterWith ="33")
    public Dictionary service;
    @ObjectFieldTypeViewAnotation(name = "ტიპი",
            filterWithField = "service:serverId:categoryId",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",requiredForSave = true,
            sqlData = true,
            canAddToDb = false, position = 3,
            filterWith ="1099")
    public Dictionary service2;
    @ObjectFieldTypeViewAnotation(name = "პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",requiredForSave = true,
            sqlData = true,
            canAddToDb = false, position = 4,
            filterWith ="67")
    public Dictionary product;

    @TextFieldTypeViewAnotation(name = "კომენტარი",requiredForSave = true, defaultValue = "",hint = "კომენტარი",type = "text", position = 5)
    public String comment;

    public String getName() {
        return product.name + " " + sum;
    }




    @LabelFieldTypeViewAnotaion(label = "წლიური შემოსავალი",position = 6)
    public String label1;

    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 7)
    public int sum;


    public static List<UrbaProductType> findbyloan(long id) {
        return UrbaProductType.find(UrbaProductType.class, "loan = ?", id + "");
    }

    public Loan loan;
}
