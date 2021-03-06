package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.DictionaryAdapter;

/**
 * Created by kaxge on 5/15/2017.
 */

public class Dictionary extends SugarRecord {

    public String name;

    public int serverId;

    public int parentId;

    public int categoryId;

    public Dictionary(){}

    public Dictionary(DictionaryAdapter adapter){
        this.name=adapter.name;
        this.serverId= adapter.id;
        this.parentId = adapter.parentId;
        this.categoryId = adapter.categoryId==1?134:adapter.categoryId==2?135:136;
    }

    public String getName(){
        return this.name;
    }

    public String type(){
        return parentId+"";
    }

    public static List<Dictionary> getData(){
        return Dictionary.listAll(Dictionary.class);
    }
    public static List<Dictionary> getData(String type){
        return Dictionary.find(Dictionary.class,"parent_id = ?",type);
    }
    public static Dictionary getById(long id){
        return Dictionary.findById(Dictionary.class,id);
    }

    @Override
    public String toString() {
        return name;
    }
}
