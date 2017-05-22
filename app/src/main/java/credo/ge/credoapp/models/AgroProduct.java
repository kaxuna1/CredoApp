package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.LabelFieldTypeViewAnotaion;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by kaxge on 5/22/2017.
 */

public class AgroProduct extends SugarRecord<AgroProduct>{

    @LabelFieldTypeViewAnotaion(label = "აგრო პროდუქტი",position = 1)
    public String label0;


    @ObjectFieldTypeViewAnotation(name = "პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 2,
            filterWith ="31")
    public Dictionary product;

    public String getName() {
        return product.name ;
    }

    @TextFieldTypeViewAnotation(name = "გამოცდილება", defaultValue = "1",type = "int", position = 3)
    public int experience;


    @ObjectFieldTypeViewAnotation(name = "ერთეული",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            sqlData = true,
            canAddToDb = false, position = 4,
            filterWith ="80")
    public Dictionary unit;

    @TextFieldTypeViewAnotation(name = "რაოდენობა", defaultValue = "1",type = "int", position = 5)
    public int quantity;

    @LabelFieldTypeViewAnotaion(label = "წლიური შემოსავალი",position = 6)
    public String label1;

    @TextFieldTypeViewAnotation(name = "რაოდენობა", defaultValue = "1",type = "int", position = 7)
    public int quantityAnual;

    @TextFieldTypeViewAnotation(name = "თანხა", defaultValue = "1",type = "int", position = 8)
    public int sum;


    public static List<AgroProduct> findbyloan(long id) {
        return AgroProduct.find(AgroProduct.class, "loan = ?", id + "");
    }

    public Loan loan;

}
