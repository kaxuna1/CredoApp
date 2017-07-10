package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import credo.ge.credoapp.StaticData;
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
    public List<Obligation> shortObligations = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "გრძელვადიანი ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 11)
    public List<LongObligation> longTermObligation = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "სასესხო ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 12)
    public List<LoanObligation> loanObligation = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "გრძელვადიანი სასესხო ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 13)
    public List<LongLoanObligation> longTermLoanObligation = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "სხვა ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 14)
    public List<OtherObligation> otherObligation = new ArrayList<>();

    @InlineObjectsListFieldTypeViewAnotation(name = "სესხი კრედოში",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "personalbalance", position = 15)
    public List<CredoObligation> credoObligations = new ArrayList<>();




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
            count+=Obligation.findbypersonalbalance(getId()).size();
            count+=LongObligation.findbypersonalbalance(getId()).size();
            count+=LoanObligation.findbypersonalbalance(getId()).size();
            count+=LongLoanObligation.findbypersonalbalance(getId()).size();
            count+=OtherObligation.findbypersonalbalance(getId()).size();
            count+=CredoObligation.findbypersonalbalance(getId()).size();
        }
        return count;
    }


    public static PersonalBalance getById(long id) {
        return PersonalBalance.findById(PersonalBalance.class, id);
    }
    public boolean isValid(){
        return StaticData.INSTANCE.checkPersonal(this);
    }


    public void initData() {
     
        shortObligations = Obligation.findbypersonalbalance(getId());
        longTermObligation = LongObligation.findbypersonalbalance(getId());
        loanObligation = LoanObligation.findbypersonalbalance(getId());
        longTermLoanObligation = LongLoanObligation.findbypersonalbalance(getId());
        otherObligation = OtherObligation.findbypersonalbalance(getId());
        credoObligations = CredoObligation.findbypersonalbalance(getId());
    }
}
