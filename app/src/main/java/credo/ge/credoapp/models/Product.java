package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.List;

import credo.ge.credoapp.anotations.TextFieldTypeViewAnotation;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.ProductAdapter;

/**
 * Created by vakhtanggelashvili on 4/27/17.
 */

public class Product extends SugarRecord {
    public String getName() {
        return product;
    }

    public String product;

    public int serverId;

    public Product(){}
    public Product(ProductAdapter adapter){
        this.serverId=adapter.id;
        this.product=adapter.product;
    }

    public static List<Product> getData(){
        return Product.listAll(Product.class);
    }
    public static Product getById(long id){
        return Product.findById(Product.class,id);
    }

    @Override
    public String toString() {
        return product==null?"":product;
    }
}
