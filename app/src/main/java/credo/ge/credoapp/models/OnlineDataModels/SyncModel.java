package credo.ge.credoapp.models.OnlineDataModels;

import java.util.ArrayList;

import credo.ge.credoapp.models.Branch;
import credo.ge.credoapp.models.Industry;
import credo.ge.credoapp.models.LoanOficer;
import credo.ge.credoapp.models.Product;
import credo.ge.credoapp.models.Purpose;
import credo.ge.credoapp.models.Vilage;
import credo.ge.credoapp.models.VilageCounsel;

/**
 * Created by kaxge on 5/10/2017.
 */

public class SyncModel {
    private ArrayList<Branch> branches;
    private ArrayList<LoanOficer> loanOfficers;
    private ArrayList<Vilage> villages;
    private ArrayList<VilageCounsel> consuls;
    private ArrayList<Purpose> purposes;
    private ArrayList<Product> products;
    private ArrayList<Industry> industries;

    public ArrayList<Branch> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<Branch> branches) {
        this.branches = branches;
    }

    public ArrayList<LoanOficer> getLoanOfficers() {
        return loanOfficers;
    }

    public void setLoanOfficers(ArrayList<LoanOficer> loanOfficers) {
        this.loanOfficers = loanOfficers;
    }

    public ArrayList<Vilage> getVillages() {
        return villages;
    }

    public void setVillages(ArrayList<Vilage> villages) {
        this.villages = villages;
    }

    public ArrayList<VilageCounsel> getConsuls() {
        return consuls;
    }

    public void setConsuls(ArrayList<VilageCounsel> consuls) {
        this.consuls = consuls;
    }

    public ArrayList<Purpose> getPurposes() {
        return purposes;
    }

    public void setPurposes(ArrayList<Purpose> purposes) {
        this.purposes = purposes;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Industry> getIndustries() {
        return industries;
    }

    public void setIndustries(ArrayList<Industry> industries) {
        this.industries = industries;
    }
}
