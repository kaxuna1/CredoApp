package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.PurposeAdapter;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class Purpose extends SugarRecord<Purpose> {
    @TextFieldTypeViewAnotation(name = "სახელი", defaultValue = "",type = "text")
    public String purpose;

    public int serverId;

    public Purpose(){}
    public Purpose(PurposeAdapter adapter){
        this.serverId = adapter.id;
        this.purpose = adapter.purpose;
    }

    public String getName() {
        return purpose;
    }


    public static List<Purpose> getData(){
        return Purpose.listAll(Purpose.class);
    }
    public static Purpose getById(long id){
        return Purpose.findById(Purpose.class,id);
    }
}
