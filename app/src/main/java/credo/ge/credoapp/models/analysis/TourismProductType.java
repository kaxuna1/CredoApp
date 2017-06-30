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
public class TourismProductType extends SugarRecord{
    @LabelFieldTypeViewAnotaion(label = "ტურისტული პროდუქტი",position = 1)
    public String label0;


    public static TourismProductType getById(long id){
        return TourismProductType.findById(TourismProductType.class,id);
    }



    @ObjectFieldTypeViewAnotation(name = "ტურისტული ზონა",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,requiredForSave = true,
            canAddToDb = false, position = 2,
            filterWith ="68")
    public Dictionary tourismZone;
    @ObjectFieldTypeViewAnotation(name = "პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",requiredForSave = true,
            sqlData = true,
            canAddToDb = false, position = 3,
            filterWith ="35")
    public Dictionary product;

    @TextFieldTypeViewAnotation(name = "რაოდენობა",requiredForSave = true, defaultValue = "0",type = "int", position = 4)
    public int quantity;

    @ObjectFieldTypeViewAnotation(name = "მდებარეობა",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",requiredForSave = true,
            sqlData = true,
            canAddToDb = false, position = 5,
            filterWith ="69")
    public Dictionary location;

    @ObjectFieldTypeViewAnotation(name = "მომსახურება",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,requiredForSave = true,
            canAddToDb = false, position = 6,
            filterWith ="36")
    public Dictionary service;
    @ObjectFieldTypeViewAnotation(name = "მომსახურების ხარისხი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",requiredForSave = true,
            sqlData = true,
            canAddToDb = false, position = 7,
            filterWith ="37")
    public Dictionary serviceQuality;


    public String getName() {
        return product.name + " " + sum;
    }




    @LabelFieldTypeViewAnotaion(label = "წლიური შემოსავალი",position = 8)
    public String label1;

    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 9)
    public int sum;


    public static List<TourismProductType> findbyloan(long id) {

        return TourismProductType.find(TourismProductType.class, "loan = ?", id + "");
    }

    public Loan loan;
}
