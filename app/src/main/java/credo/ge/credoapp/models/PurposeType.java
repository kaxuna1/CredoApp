package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.PurposeAdapter;

/**
 * Created by kaxge on 6/26/2017.
 */


public class PurposeType extends SugarRecord<PurposeType> {
    public String purpose;

    public int serverId;
    public int type;

    public PurposeType(){}
    public PurposeType(PurposeAdapter adapter){
        this.serverId = adapter.id;
        this.purpose = adapter.purpose;
        this.type  = adapter.type;
    }

    public String getName() {
        return purpose;
    }


    public static List<PurposeType> getData(){
        return PurposeType.listAll(PurposeType.class);
    }
    public static PurposeType getById(long id){
        return PurposeType.findById(PurposeType.class,id);
    }

    @Override
    public String toString() {
        return purpose;
    }
}
