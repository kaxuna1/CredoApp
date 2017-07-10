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
 * Created by kaxge on 6/8/2017.
 */
@ParserClassAnnotation
public class BusinessBalance extends SugarRecord {
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
    public List<ObligationsToReceive> obligationsToReceives = new ArrayList<>();

    @InlineObjectsListFieldTypeViewAnotation(name = "მზა პროდუქტი",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 4)
    public List<ReadyProduct> readyProduct = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "ნედლეული/მარაგები",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 5)
    public List<Materials> materialses = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "პირუტყვი",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 6)
    public List<Cattle> cattle = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "მანქანა/დანადგარები",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 7)
    public List<Machines> machines = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "შენობა/ნაგებობა",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 8)
    public List<Building> buildings = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "მიწა",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 9)
    public List<Land> lands = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "მოკლევადიანი ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 10)
    public List<Obligation> shortObligations = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "გრძელვადიანი ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 11)
    public List<LongObligation> longTermObligation = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "სასესხო ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 12)
    public List<LoanObligation> loanObligation = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "გრძელვადიანი სასესხო ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 13)
    public List<LongLoanObligation> longTermLoanObligation = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "სხვა ვალდებულება",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 14)
    public List<OtherObligation> otherObligation = new ArrayList<>();
    @InlineObjectsListFieldTypeViewAnotation(name = "სესხი კრედოში",
            displayField = "getName",
            isMethod = true,
            type = "inlineView",
            sqlData = true,
            canAddToDb = false,
            joinField = "businessbalance", position = 15)
    public List<CredoObligation> credoObligations = new ArrayList<>();


    public BusinessBalance() {
        try {
            cash.save();
            balance.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isValid(){
        return StaticData.INSTANCE.checkBusiness(this);
    }


    public int getCount() {
        int count = 0;

        if (cash.sum > 0)
            count++;
        if (balance.sum > 0)
            count++;
        if (getId() != null) {
            count += ObligationsToReceive.findbybusinessbalance(getId()).size();
            count += ReadyProduct.findbybusinessbalance(getId()).size();
            count += Materials.findbybusinessbalance(getId()).size();
            count += Cattle.findbybusinessbalance(getId()).size();
            count += Machines.findbybusinessbalance(getId()).size();
            count += Building.findbybusinessbalance(getId()).size();
            count += Land.findbybusinessbalance(getId()).size();
            count += Obligation.findbybusinessbalance(getId()).size();
            count += LongObligation.findbybusinessbalance(getId()).size();
            count += LoanObligation.findbybusinessbalance(getId()).size();
            count += LongLoanObligation.findbybusinessbalance(getId()).size();
            count += OtherObligation.findbybusinessbalance(getId()).size();
            count += CredoObligation.findbybusinessbalance(getId()).size();

        }
        return count;
    }


    public static BusinessBalance getById(long id) {
        return BusinessBalance.findById(BusinessBalance.class, id);
    }


    public void initData() {
        obligationsToReceives = ObligationsToReceive.findbybusinessbalance(getId());
        readyProduct = ReadyProduct.findbybusinessbalance(getId());
        materialses = Materials.findbybusinessbalance(getId());
        cattle = Cattle.findbybusinessbalance(getId());
        machines = Machines.findbybusinessbalance(getId());
        buildings = Building.findbybusinessbalance(getId());
        lands = Land.findbybusinessbalance(getId());
        shortObligations = Obligation.findbybusinessbalance(getId());
        longTermObligation = LongObligation.findbybusinessbalance(getId());
        loanObligation = LoanObligation.findbybusinessbalance(getId());
        longTermLoanObligation = LongLoanObligation.findbybusinessbalance(getId());
        otherObligation = OtherObligation.findbybusinessbalance(getId());
        credoObligations = CredoObligation.findbybusinessbalance(getId());
    }
}
