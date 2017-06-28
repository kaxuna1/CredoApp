package credo.ge.credoapp.models.ViewModels;

import android.widget.ListView;

import java.util.List;

import credo.ge.credoapp.anotations.ListViewFieldTypeViewAnnotation;
import credo.ge.credoapp.anotations.ObjectsListFieldTypeViewAnottion;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.models.Loan;
import credo.ge.credoapp.models.analysis.ExpansesListItem;

/**
 * Created by kaxge on 6/9/2017.
 */
@ParserClassAnnotation(cols = {"გადაგზავნილი","დაუსრულებელი"},dataModel = false)
public class LoansViewer {
    @ObjectsListFieldTypeViewAnottion(name = "სესხები",
            displayField = "getName",
            isMethod = true,
            type = "list",
            sqlData = true,
            canAddToDb = false,
            objectType = "loan",
            joinField = "loan", position = 1,page = 0,filter = "1")
    public List<Loan> loen;
    @ObjectsListFieldTypeViewAnottion(name = "სესხები",
            displayField = "getName",
            isMethod = true,
            type = "list",
            sqlData = true,
            canAddToDb = false,
            objectType = "loan",
            joinField = "loan", position = 2,page = 1,filter = "2")
    public List<Loan> loanList;



    public void init(){

    }
}
