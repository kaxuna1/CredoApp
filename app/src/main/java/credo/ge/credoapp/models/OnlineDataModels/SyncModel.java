package credo.ge.credoapp.models.OnlineDataModels;

import java.util.ArrayList;

import credo.ge.credoapp.models.Industry;
import credo.ge.credoapp.models.LoanOficer;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.BranchAdapterClass;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.DictionaryAdapter;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.IndustryAdapter;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.LoanOficerAdapter;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.ProductAdapter;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.PurposeAdapter;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.VilageAdapter;
import credo.ge.credoapp.models.OnlineDataModels.OnlineDataAdapters.VilageCounselAdapter;
import credo.ge.credoapp.models.Product;
import credo.ge.credoapp.models.Purpose;
import credo.ge.credoapp.models.Vilage;
import credo.ge.credoapp.models.VilageCounsel;

/**
 * Created by kaxge on 5/10/2017.
 */

public class SyncModel {
    private ArrayList<BranchAdapterClass> branches;
    private ArrayList<LoanOficerAdapter> loanOfficers;
    private ArrayList<VilageAdapter> villages;
    private ArrayList<VilageCounselAdapter> consuls;
    private ArrayList<PurposeAdapter> purposesTypes;
    private ArrayList<PurposeAdapter> purposes;
    private ArrayList<ProductAdapter> products;
    private ArrayList<IndustryAdapter> industries;
    private ArrayList<DictionaryAdapter> dictionaries;

    public ArrayList<BranchAdapterClass> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<BranchAdapterClass> branches) {
        this.branches = branches;
    }

    public ArrayList<LoanOficerAdapter> getLoanOfficers() {
        return loanOfficers;
    }

    public void setLoanOfficers(ArrayList<LoanOficerAdapter> loanOfficers) {
        this.loanOfficers = loanOfficers;
    }

    public ArrayList<VilageAdapter> getVillages() {
        return villages;
    }

    public void setVillages(ArrayList<VilageAdapter> villages) {
        this.villages = villages;
    }

    public ArrayList<VilageCounselAdapter> getConsuls() {
        return consuls;
    }

    public void setConsuls(ArrayList<VilageCounselAdapter> consuls) {
        this.consuls = consuls;
    }

    public ArrayList<PurposeAdapter> getPurposes() {
        return purposes;
    }

    public void setPurposes(ArrayList<PurposeAdapter> purposes) {
        this.purposes = purposes;
    }

    public ArrayList<ProductAdapter> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductAdapter> products) {
        this.products = products;
    }

    public ArrayList<IndustryAdapter> getIndustries() {
        return industries;
    }

    public void setIndustries(ArrayList<IndustryAdapter> industries) {
        this.industries = industries;
    }

    public ArrayList<DictionaryAdapter> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(ArrayList<DictionaryAdapter> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public ArrayList<PurposeAdapter> getPurposesTypes() {
        return purposesTypes;
    }

    public void setPurposesTypes(ArrayList<PurposeAdapter> purposesTypes) {
        this.purposesTypes = purposesTypes;
    }
}
