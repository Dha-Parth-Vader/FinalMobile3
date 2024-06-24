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
        paint.setTextSize(fontSize);
        paint.setColor(Color.BLACK);
        Typeface typeface = Typeface.create("sans-serif", Typeface.BOLD);
        paint.setTypeface(typeface);
        String name = "Aaron Huang";

        float textWidth = paint.measureText(name);

        // Calculate the starting x position for the text to be centered
        float x = (canvasWidth - textWidth) / 2;
        page.getCanvas().drawText(name, x, 50, paint);

        // Draw a line
        page.getCanvas().drawLine(15, 65, 597, 65, paint);

        // Draw academic achievement title
        paint.setTextSize(25);
        page.getCanvas().drawText("Academic Achievement", 10, 95, paint);

        // Draw long text with wrapping
        paint.setTextSize(15);
        String longText = "H IHEI HDIFH IEHF HIXHFSIAHD IFHEIH SIHIFEH IHDHFI HEF HI HFI HDH FIEHF HI DH IEHFIH DFIH DFH IEHFIHDIHFIHEFIHDFIH IEHIFHEIHFIDH IE HEI EIH EI HEI";
        float y = 125;
        float lineHeight = paint.getTextSize() + 5; // Adding some space between lines

        PageWrapper pageWrapper = new PageWrapper(document, page, pageInfo, y, lineHeight, canvasWidth, canvasHeight);
        pageWrapper = longTextStuff(longText, paint, 10, pageWrapper);

        // Change font size and update line height
//        paint.setTextSize(35);
//        lineHeight = paint.getTextSize() + 5; // Update lineHeight for new font size
        pageWrapper.updateLineHeight(lineHeight);
        pageWrapper = longTextStuff("Athletic Participation", paint, 10, pageWrapper);

        y = pageWrapper.getY();
        page = pageWrapper.getPage();

        document.finishPage(page);

        // Save the document to a file
        File pdfFile = new File(context.getExternalCacheDir(), "hello_world.pdf");
        FileOutputStream fos = new FileOutputStream(pdfFile);
        document.writeTo(fos);
        document.close();
        fos.close();

        return pdfFile;
    }

    public static PageWrapper longTextStuff(String longText, Paint paint, float x, PageWrapper pageWrapper) {
        PdfDocument document = pageWrapper.getDocument();
        PdfDocument.Page page = pageWrapper.getPage();
        PdfDocument.PageInfo pageInfo = pageWrapper.getPageInfo();
        float y = pageWrapper.getY();
        float lineHeight = pageWrapper.getLineHeight();
        int canvasHeight = pageWrapper.getCanvasHeight();
        int canvasWidth = pageWrapper.getCanvasWidth();

        int start = 0;
        int end;

        while (start < longText.length()) {
            end = paint.breakText(longText, start, longText.length(), true, canvasWidth - 20, null);
            String line = longText.substring(start, start + end);
            page.getCanvas().drawText(line, x, y, paint);
            start += end;
            y += lineHeight;
            if (y > canvasHeight - 20) {
                // Finish the current page and start a new one
                document.finishPage(page);
                pageInfo = new PdfDocument.PageInfo.Builder(canvasWidth, canvasHeight, pageInfo.getPageNumber() + 1).create();
                page = document.startPage(pageInfo);
                y = 20;
            }
        }

        return new PageWrapper(document, page, pageInfo, y, lineHeight, canvasWidth, canvasHeight);
    }

    static class PageWrapper {
        private PdfDocument document;
        private PdfDocument.Page page;
        private PdfDocument.PageInfo pageInfo;
        private float y;
        private float lineHeight;
        private int canvasWidth;
        private int canvasHeight;

        public PageWrapper(PdfDocument document, PdfDocument.Page page, PdfDocument.PageInfo pageInfo, float y, float lineHeight, int canvasWidth, int canvasHeight) {
            this.document = document;
            this.page = page;
            this.pageInfo = pageInfo;
            this.y = y;
            this.lineHeight = lineHeight;
            this.canvasWidth = canvasWidth;
            this.canvasHeight = canvasHeight;
        }

        public PdfDocument getDocument() {
            return document;
        }

        public PdfDocument.Page getPage() {
            return page;
        }

        public PdfDocument.PageInfo getPageInfo() {
            return pageInfo;
        }

        public float getY() {
            return y;
        }

        public float getLineHeight() {
            return lineHeight;
        }

        public void updateLineHeight(float newLineHeight) {
            this.lineHeight = newLineHeight;
        }

        public int getCanvasWidth() {
            return canvasWidth;
        }

        public int getCanvasHeight() {
            return canvasHeight;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}