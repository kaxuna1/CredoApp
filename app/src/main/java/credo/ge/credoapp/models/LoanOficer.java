package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.LoanOficerAdapter;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class LoanOficer extends SugarRecord<LoanOficer> {
    public String loanOfficer;

    public int branchId;

    public int serverId;

    public LoanOficer(){}
    public LoanOficer(LoanOficerAdapter adapterItem){
        this.branchId=adapterItem.branchId;
        this.serverId=adapterItem.id;
        this.loanOfficer=adapterItem.loanOfficer;
    }

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

    @Override
    public String toString() {
        return loanOfficer==null?"":loanOfficer;
    }
}
