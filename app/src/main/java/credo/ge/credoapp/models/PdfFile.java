package credo.ge.credoapp.models;

import com.orm.SugarRecord;

/**
 * Created by kaxge on 5/18/2017.
 */

public class PdfFile extends SugarRecord<PdfFile> {
    public String pId;

    public byte[] pdf;
}
