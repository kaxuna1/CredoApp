package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.ArrayList;

import credo.ge.credoapp.anotations.DataGroupFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.InlineObjectsListFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ObjectsListFieldTypeViewAnottion;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.models.Cash;
import credo.ge.credoapp.models.Loan;

/**
 * Created by kaxge on 6/8/2017.
 */
@ParserClassAnnotation
public class BusinessBalance extends SugarRecord<BusinessBalance> {
    @DataGroupFieldTypeViewAnotation(name = "ნაღდი ფული", position = 1)
    public Cash cash = new Cash();

    @DataGroupFieldTypeViewAnotation(name = "ფული ანგარიშზე", position = 2)
    public Balance balance = new Balance();

    @InlineObjectsListFieldTypeViewAnotation(name = "მისაღები ვალდებულებები",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessBalance", position = 3)
    public ArrayList<ObligationsToReceive> obligationsToReceives;

    public BusinessBalance() {
        try {
            cash.save();
            balance.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getCount() {
        int count = 0;

        if (cash.sum > 0)
            count++;
        if (balance.sum > 0)
            count++;


        return count;
    }


    public static BusinessBalance getById(long id) {
        return BusinessBalance.findById(BusinessBalance.class, id);
    }


}
