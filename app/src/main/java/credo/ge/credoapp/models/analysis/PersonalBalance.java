package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.DataGroupFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.InlineObjectsListFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.models.Cash;

/**
 * Created by kaxge on 6/9/2017.
 */
@ParserClassAnnotation
public class PersonalBalance extends SugarRecord {
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
    public List<Obligation> shortObligations;
    @InlineObjectsListFieldTypeViewAnotation(name = "გრძელვადიანი ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 11)
    public List<LongObligation> longTermObligation;
    @InlineObjectsListFieldTypeViewAnotation(name = "სასესხო ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 12)
    public List<LoanObligation> loanObligation;
    @InlineObjectsListFieldTypeViewAnotation(name = "გრძელვადიანი სასესხო ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 13)
    public List<LongLoanObligation> longTermLoanObligation;
    @InlineObjectsListFieldTypeViewAnotation(name = "სხვა ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 14)
    public List<OtherObligation> otherObligation;

    @InlineObjectsListFieldTypeViewAnotation(name = "სესხი კრედოში",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 15)
    public List<CredoObligation> credoObligations;




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
        if(getId()!=null){
            count+=Obligation.findbybusinessbalance(getId()).size();
            count+=LongObligation.findbybusinessbalance(getId()).size();
            count+=LoanObligation.findbybusinessbalance(getId()).size();
            count+=LongLoanObligation.findbybusinessbalance(getId()).size();
            count+=OtherObligation.findbybusinessbalance(getId()).size();
            count+=CredoObligation.findbybusinessbalance(getId()).size();
        }
        return count;
    }


    public static PersonalBalance getById(long id) {
        return PersonalBalance.findById(PersonalBalance.class, id);
    }


    public void initData() {
     
        shortObligations = Obligation.findbybusinessbalance(getId());
        longTermObligation = LongObligation.findbybusinessbalance(getId());
        loanObligation = LoanObligation.findbybusinessbalance(getId());
        longTermLoanObligation = LongLoanObligation.findbybusinessbalance(getId());
        otherObligation = OtherObligation.findbybusinessbalance(getId());
        credoObligations = CredoObligation.findbybusinessbalance(getId());
    }
}
