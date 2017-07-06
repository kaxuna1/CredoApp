package credo.ge.credoapp.models.OnlineDataModels;

import java.util.ArrayList;
import java.util.List;

import credo.ge.credoapp.models.Loan;

/**
 * Created by kaxge on 5/10/2017.
 */

public class MethodName {

    private String method;

    private Loan loan;

    private String userId="";

    private List<ImageDataModel> images = new ArrayList<>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public MethodName() {

    }
    public MethodName(String method,Loan loan,List<ImageDataModel> images,String userId){
        this.method = method;
        this.loan = loan;
        this.images = images;
        this.userId = userId;
    }

    public MethodName(String method) {
        this.method = method;
    }
    public MethodName(String method,Loan loan) {
        this.method = method;
        this.loan = loan;
    }
    public MethodName(String method,String userId) {
        this.method = method;
        this.userId = userId;
    }

    public List<ImageDataModel> getImages() {
        return images;
    }

    public void setImages(List<ImageDataModel> images) {
        this.images = images;
    }
}
