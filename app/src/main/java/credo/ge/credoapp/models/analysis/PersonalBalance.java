package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.ArrayList;

import credo.ge.credoapp.anotations.DataGroupFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.InlineObjectsListFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.models.Cash;

/**
 * Created by kaxge on 6/9/2017.
 */
@ParserClassAnnotation
public class PersonalBalance extends SugarRecord<PersonalBalance> {
    @DataGroupFieldTypeViewAnotation(name = "მოძრავი აქტივი", position = 1)
    public Asset mobileAsset = new Asset();

    @DataGroupFieldTypeViewAnotation(name = "უძრავი აქტივი", position = 2)
    public Asset stillAsset = new Asset();

    @InlineObjectsListFieldTypeViewAnotation(name = "მოკლევადიანი ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 10)
    public ArrayList<Obligation> shortObligations;
    @InlineObjectsListFieldTypeViewAnotation(name = "გრძელვადიანი ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 11)
    public ArrayList<LongObligation> longTermObligation;
    @InlineObjectsListFieldTypeViewAnotation(name = "სასესხო ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 12)
    public ArrayList<LoanObligation> loanObligation;
    @InlineObjectsListFieldTypeViewAnotation(name = "გრძელვადიანი სასესხო ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 13)
    public ArrayList<LongLoanObligation> longTermLoanObligation;
    @InlineObjectsListFieldTypeViewAnotation(name = "სხვა ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 14)
    public ArrayList<OtherObligation> otherObligation;

    @InlineObjectsListFieldTypeViewAnotation(name = "სესხი კრედოში",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 15)
    public ArrayList<CredoObligation> credoObligations;




    public PersonalBalance() {
        try {
            mobileAsset.save();
            stillAsset.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getCount() {
        int count = 0;

        if (mobileAsset.sum > 0)
            count++;
        if (stillAsset.sum > 0)
            count++;
        if(id!=null){
            count+=Obligation.findbybusinessbalance(id).size();
            count+=LongObligation.findbybusinessbalance(id).size();
            count+=LoanObligation.findbybusinessbalance(id).size();
            count+=LongLoanObligation.findbybusinessbalance(id).size();
            count+=OtherObligation.findbybusinessbalance(id).size();
            count+=CredoObligation.findbybusinessbalance(id).size();
        }
        return count;
    }


    public static PersonalBalance getById(long id) {
        return PersonalBalance.findById(PersonalBalance.class, id);
    }


}