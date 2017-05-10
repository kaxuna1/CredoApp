package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;

/**
 * Created by vakhtanggelashvili on 5/3/17.
 */

public class VilageCounsel extends SugarRecord<VilageCounsel> {
    @TextFieldTypeViewAnotation(name = "სახელი", defaultValue = "",type = "text")
    public String consul;

    public int villageId;

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
}
