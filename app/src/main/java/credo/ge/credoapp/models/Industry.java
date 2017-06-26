package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.IndustryAdapter;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class Industry extends SugarRecord<Industry>{
    public String industry;

    public int serverId;

    public int sectorId;

    public String getName() {
        return industry;
    }

    public Industry(){}
    public Industry(IndustryAdapter adapterItem){
        this.industry=adapterItem.industry;
        this.serverId=adapterItem.id;
        this.sectorId=adapterItem.sectorId;
    }


    public static List<Industry> getData(){
        return Industry.listAll(Industry.class);
    }
    public static Industry getById(long id){
        return Industry.findById(Industry.class,id);
    }

}
