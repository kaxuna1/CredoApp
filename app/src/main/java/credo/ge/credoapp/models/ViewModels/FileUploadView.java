package credo.ge.credoapp.models.ViewModels;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import credo.ge.credoapp.R;
import credo.ge.credoapp.StaticData;
import credo.ge.credoapp.StaticJava;
import credo.ge.credoapp.anotations.ButtonFieldTypeViewAnnotation;
import credo.ge.credoapp.anotations.FileUploadAnnotation;
import credo.ge.credoapp.anotations.ObjectFieldTypeViewAnotation;
import credo.ge.credoapp.anotations.ParserClassAnnotation;
import credo.ge.credoapp.models.Loan;
import credo.ge.credoapp.models.OnlineDataModels.ImageDataModel;
import credo.ge.credoapp.models.OnlineDataModels.SyncLoanResult;
import credo.ge.credoapp.models.Person;
import credo.ge.credoapp.models.UploadFileParameters;
import credo.ge.credoapp.models.analysis.BusinessBalance;
import credo.ge.credoapp.models.analysis.PersonalBalance;
import credo.ge.credoapp.online.OnlineData;
import credo.ge.credoapp.sent_loan_page;
import rx.functions.Action1;

/**
 * Created by kaxge on 7/4/2017.
 */
@ParserClassAnnotation(cols = {"ფაილის ატვირთვა"},dataModel = false)
public class FileUploadView {
    @ObjectFieldTypeViewAnotation(name = "სესხი",
            displayField = "getName",
            isMethod = true,
            type = "comboBox",
            booleanFilterField = "sent",
            booleanFilterVal = true,
            sqlData = true,
            requiredForSave = true,
            canAddToDb = false, position = 0)
    public Loan loan;

    @FileUploadAnnotation(name = "სურათები",page = 0,position = 1)
    public List<UploadFileParameters>  image_list = new ArrayList<>();
    @ButtonFieldTypeViewAnnotation(name = "ატვირთვა", position = 28, icon = R.drawable.cloudup)
    public View.OnClickListener sendClick2 = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {


            if (StaticData.INSTANCE.isNetworkAvailable(v.getContext())) {

                try{
                    final ACProgressFlower dialog = new ACProgressFlower.Builder(v.getContext())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(Color.WHITE)
                            .text("რეგისტრაცია")
                            .fadeColor(Color.DKGRAY).build();
                    dialog.show();

                    List<ImageDataModel> images = new ArrayList<>();

                    int i = 1;
                    for (UploadFileParameters item : image_list) {
                        String imageBase64 = StaticJava.encodeToBase64(item.bitmap,Bitmap.CompressFormat.JPEG,60);
                        ImageDataModel imageDataModel = new ImageDataModel();
                        imageDataModel.setData(imageBase64);
                        imageDataModel.setName("imageFile"+i+".jpeg");
                        imageDataModel.setType(item.type);
                        i++;
                        images.add(imageDataModel);

                    }

                    OnlineData.INSTANCE.syncFiles(images, loan, new Action1<SyncLoanResult>() {
                        @Override
                        public void call(SyncLoanResult syncLoanResult) {
                            if(syncLoanResult!=null){
                                dialog.hide();
                                Snackbar.make(v, "ფაილები ატვირთულია", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                Intent intent = new Intent(v.getContext(), sent_loan_page.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.putExtra("id", loan.getId());
                                v.getContext().startActivity(intent);
                            }
                            dialog.hide();
                            Intent intent = new Intent(v.getContext(), sent_loan_page.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("id", loan.getId());
                            v.getContext().startActivity(intent);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                    Snackbar.make(v, "ფაილების ატვირთვის დროს მოხდა შეცდომა!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            } else {
                Snackbar.make(v, "ფაილების ასატვირთად საჭიროა ინტერნეტ კავშირი!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }


        }
    };
}
