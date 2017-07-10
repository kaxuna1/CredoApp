package credo.ge.credoapp.models.OnlineDataModels;

import java.util.List;

import credo.ge.credoapp.models.CurrencyData;

/**
 * Created by kaxge on 6/13/2017.
 */

public class SyncLoanDataModel {
    public boolean success;
    public String errorMessage;
    public SyncStatusResult result;
    public String position;
    public int loanID;
    public int personID;
    public List<CurrencyData>currencyData;
}
