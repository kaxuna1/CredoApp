package credo.ge.credoapp.models.OnlineDataModels;

import java.util.Date;

/**
 * Created by vakhtanggelashvili on 7/10/17.
 */

public class SyncStatusResult{
    private int applicationStatusId;
    private float amount;
    private String currencyName;
    private int period;
    private float intRate;
    private float effectivePercent;
    private float loanCommission;
    private float smsCommission;
    private float payment;
    private String statusChangeDate;


    public int getApplicationStatusId() {
        return applicationStatusId;
    }

    public void setApplicationStatusId(int applicationStatusId) {
        this.applicationStatusId = applicationStatusId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public float getIntRate() {
        return intRate;
    }

    public void setIntRate(float intRate) {
        this.intRate = intRate;
    }

    public float getEffectivePercent() {
        return effectivePercent;
    }

    public void setEffectivePercent(float effectivePercent) {
        this.effectivePercent = effectivePercent;
    }

    public float getLoanCommission() {
        return loanCommission;
    }

    public void setLoanCommission(float loanCommission) {
        this.loanCommission = loanCommission;
    }

    public float getSmsCommission() {
        return smsCommission;
    }

    public void setSmsCommission(float smsCommission) {
        this.smsCommission = smsCommission;
    }

    public float getPayment() {
        return payment;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

    public String getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(String statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

}
