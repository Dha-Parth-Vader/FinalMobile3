package com.example.myapplication;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfUtils {

    public static File createPdf(Context context) throws IOException {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 300, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Paint paint = new Paint();
        paint.setTextSize(16);
        page.getCanvas().drawText("Hello World", 80, 50, paint);

        document.finishPage(page);

        File pdfFile = new File(context.getExternalCacheDir(), "hello_world.pdf");
        FileOutputStream fos = new FileOutputStream(pdfFile);
        document.writeTo(fos);
        document.close();
        fos.close();

        return pdfFile;
    }
}