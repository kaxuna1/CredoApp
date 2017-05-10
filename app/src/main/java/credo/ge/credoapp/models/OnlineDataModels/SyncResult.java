package credo.ge.credoapp.models.OnlineDataModels;

/**
 * Created by kaxge on 5/10/2017.
 */

public class SyncResult {
    private SyncData data;

    private String message;

    public SyncData getData() {
        return data;
    }

    public void setData(SyncData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
