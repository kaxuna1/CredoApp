package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class LoanOficer extends SugarRecord<LoanOficer> {
    @TextFieldTypeViewAnotation(name = "სახელი",deffaultValue = "",type = "text")
    public String loanOfficer;

    public int branchId;


    public String getName() {
        return loanOfficer;
    }


    public static List<LoanOficer> getData(){
        return LoanOficer.listAll(LoanOficer.class);
    }
    public static LoanOficer getById(long id){
        return LoanOficer.findById(LoanOficer.class,id);
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}
