package credo.ge.credoapp.models.analysis;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import credo.ge.credoapp.anotations.DataGroupFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.InlineObjectsListFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.models.Cash;

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
            joinField = "businessbalance", position = 3)
    public List<ObligationsToReceive> obligationsToReceives;

    @InlineObjectsListFieldTypeViewAnotation(name = "მზა პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 4)
    public List<ReadyProduct> readyProduct;
    @InlineObjectsListFieldTypeViewAnotation(name = "ნედლეული/მარაგები",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 5)
    public List<Materials> materialses;
    @InlineObjectsListFieldTypeViewAnotation(name = "პირუტყვი",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 6)
    public List<Cattle> cattle;
    @InlineObjectsListFieldTypeViewAnotation(name = "მანქანა/დანადგარები",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 7)
    public List<Machines> machines;
    @InlineObjectsListFieldTypeViewAnotation(name = "შენობა/ნაგებობა",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 8)
    public List<Building> buildings;
    @InlineObjectsListFieldTypeViewAnotation(name = "მიწა",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 9)
    public List<Land> lands;
    @InlineObjectsListFieldTypeViewAnotation(name = "მოკლევადიანი ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 10)
    public List<Obligation> shortObligations;
    @InlineObjectsListFieldTypeViewAnotation(name = "გრძელვადიანი ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 11)
    public List<LongObligation> longTermObligation;
    @InlineObjectsListFieldTypeViewAnotation(name = "სასესხო ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 12)
    public List<LoanObligation> loanObligation;
    @InlineObjectsListFieldTypeViewAnotation(name = "გრძელვადიანი სასესხო ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 13)
    public List<LongLoanObligation> longTermLoanObligation;
    @InlineObjectsListFieldTypeViewAnotation(name = "სხვა ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 14)
    public List<OtherObligation> otherObligation;
    @InlineObjectsListFieldTypeViewAnotation(name = "სესხი კრედოში",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 15)
    public List<CredoObligation> credoObligations;


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
        if (id != null) {
            count += ObligationsToReceive.findbybusinessbalance(id).size();
            count += ReadyProduct.findbybusinessbalance(id).size();
            count += Materials.findbybusinessbalance(id).size();
            count += Cattle.findbybusinessbalance(id).size();
            count += Machines.findbybusinessbalance(id).size();
            count += Building.findbybusinessbalance(id).size();
            count += Land.findbybusinessbalance(id).size();
            count += Obligation.findbybusinessbalance(id).size();
            count += LongObligation.findbybusinessbalance(id).size();
            count += LoanObligation.findbybusinessbalance(id).size();
            count += LongLoanObligation.findbybusinessbalance(id).size();
            count += OtherObligation.findbybusinessbalance(id).size();
            count += CredoObligation.findbybusinessbalance(id).size();

        }
        return count;
    }


    public static BusinessBalance getById(long id) {
        return BusinessBalance.findById(BusinessBalance.class, id);
    }


    public void initData() {
        obligationsToReceives = ObligationsToReceive.findbybusinessbalance(id);
        readyProduct = ReadyProduct.findbybusinessbalance(id);
        materialses = Materials.findbybusinessbalance(id);
        cattle = Cattle.findbybusinessbalance(id);
        machines = Machines.findbybusinessbalance(id);
        buildings = Building.findbybusinessbalance(id);
        lands = Land.findbybusinessbalance(id);
        shortObligations = Obligation.findbybusinessbalance(id);
        longTermObligation = LongObligation.findbybusinessbalance(id);
        loanObligation = LoanObligation.findbybusinessbalance(id);
        longTermLoanObligation = LongLoanObligation.findbybusinessbalance(id);
        otherObligation = OtherObligation.findbybusinessbalance(id);
        credoObligations = CredoObligation.findbybusinessbalance(id);
    }
}
