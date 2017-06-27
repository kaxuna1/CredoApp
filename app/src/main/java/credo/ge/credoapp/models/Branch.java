package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.BranchAdapterClass;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class Branch extends SugarRecord {


    public String branch;

    public String city;

    public long serverId;

    public String getName() {
        return branch;
    }
    public Branch(){

    }
    public Branch(BranchAdapterClass adapterClassItem){
        this.branch=adapterClassItem.branch;
        this.serverId=adapterClassItem.id;
        this.city=adapterClassItem.city;
    }


    public static List<Branch> getData() {
        return Branch.listAll(Branch.class);
    }

    public static Branch getById(long id) {
        return Branch.findById(Branch.class, id);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return branch==null?"":branch;
    }
}
