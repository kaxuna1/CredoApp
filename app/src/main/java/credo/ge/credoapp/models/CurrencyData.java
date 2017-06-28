package credo.ge.credoapp.models;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by kaxge on 6/28/2017.
 */

public class CurrencyData extends SugarRecord {
    private String code;
    private float rate;
    private float diff;
    private String date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getDiff() {
        return diff;
    }

    public void setDiff(float diff) {
        this.diff = diff;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
