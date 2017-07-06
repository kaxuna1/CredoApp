package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.MobileExpanseTypesAdapter;

/**
 * Created by vakhtanggelashvili on 7/6/17.
 */

public class ExpanseType extends SugarRecord{
    public String name;
    public int serverid;
    public float amount;
    public String cur;
    public ExpanseType(){}
    public ExpanseType(MobileExpanseTypesAdapter expanseTypesAdapter){
        this.name = expanseTypesAdapter.name;
        this.serverid = expanseTypesAdapter.id;
        this.amount = expanseTypesAdapter.amount;
        this.cur = expanseTypesAdapter.cur;
    }
    public String getName(){
        return name+" "+amount+""+cur;
    }

    public String type(){
        return serverid+"";
    }

    public static List<ExpanseType> getData(){
        return ExpanseType.listAll(ExpanseType.class);
    }

    public static ExpanseType getById(long id){
        return ExpanseType.findById(ExpanseType.class,id);
    }

    @Override
    public String toString() {
        return name+" "+amount+""+cur;
    }
}
