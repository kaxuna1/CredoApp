package credo.ge.credoapp.models.OnlineDataModels;

/**
 * Created by vakhtanggelashvili on 5/4/17.
 */

public class LoginData {
    private String access_token;



    private String BranchId;
    private String LoanOfficerId;
    private String Status;
    private String UserId;
    private String token_type;
    private long expires_in;

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public String getLoanOfficerId() {
        return LoanOfficerId;
    }

    public void setLoanOfficerId(String loanOfficerId) {
        LoanOfficerId = loanOfficerId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}