package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.VilageCounselAdapter;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class VilageCounsel extends SugarRecord<VilageCounsel> {
    public String consul;

    public int villageId;

    public long serverId;

    public VilageCounsel(){

    }
    public VilageCounsel(VilageCounselAdapter adapterItem){
        this.consul=adapterItem.consul;
        this.villageId=adapterItem.villageId;
        this.serverId=adapterItem.id;
    }

    public String getName() {
        return consul;
    }


    public static List<VilageCounsel> getData(){
        return VilageCounsel.listAll(VilageCounsel.class);
    }
    public static VilageCounsel getById(long id){
        return LoanOficer.findById(VilageCounsel.class,id);
    }

    public int getVillageId() {
        return villageId;
    }

    public void setVillageId(int villageId) {
        this.villageId = villageId;
    }

    @Override
    public String toString() {
        return consul;
    }
}
