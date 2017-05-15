package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.VilageAdapter;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class Vilage extends SugarRecord {

    public String village;

    public String city;

    public int branchId;

    public int serverId;

    public Vilage(){}

    public Vilage(VilageAdapter adapter){
        this.serverId = adapter.id;
        this.branchId = adapter.branchId;
        this.city = adapter.city;
        this.village = adapter.village;
    }

    public String getName() {
        return village;
    }


    public static List<Vilage> getData(){
        return Vilage.listAll(Vilage.class);
    }
    public static Vilage getById(long id){
        return Vilage.findById(Vilage.class,id);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}
