package credo.ge.credoapp.models.OnlineDataModels;

/**
 * Created by kaxge on 5/18/2017.
 */

public class AutoCheckMethod {
    private String method;

    private String branchId;

    private String byUserID;

    private String personalNo;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public AutoCheckMethod() {
    }

    public AutoCheckMethod(String method, String branchId, String byUserID, String personalNo) {
        this.method = method;
        this.branchId = branchId;
        this.byUserID = byUserID;
        this.personalNo = personalNo;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getByUserID() {
        return byUserID;
    }

    public void setByUserID(String byUserID) {
        this.byUserID = byUserID;
    }

    public String getPersonalNo() {
        return personalNo;
    }

    public void setPersonalNo(String personalNo) {
        this.personalNo = personalNo;
    }
}
