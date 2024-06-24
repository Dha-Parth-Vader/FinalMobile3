package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfUtils {

    public static File createPdf(Context context) throws IOException {
        PdfDocument document = new PdfDocument();
        int canvasWidth = 612;
        int canvasHeight = 792;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(canvasWidth, canvasHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Paint paint = new Paint();
        int fontSize = 35;
        paint.setTextSize(35);
        paint.setColor(Color.BLACK);
        Typeface typeface;
        typeface = Typeface.create("sans-serif", Typeface.BOLD);
        paint.setTypeface(typeface);
        String name = "Aaron Huang";

        float textWidth = paint.measureText(name);

        // Calculate the starting x position for the text to be centered
        float x = (canvasWidth - textWidth) / 2;


        page.getCanvas().drawText(name, x, 50, paint);


        page.getCanvas().drawLine(15, 65,597,65,paint);









        //finishing
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