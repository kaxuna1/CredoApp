package credo.ge.credoapp.models.OnlineDataModels;

import credo.ge.credoapp.models.Loan;

/**
 * Created by kaxge on 5/10/2017.
 */

public class MethodName {

    private String method;

    private Loan loan;

    private String userId="";

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public MethodName() {

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
}
