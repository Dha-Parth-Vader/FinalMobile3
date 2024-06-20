package com.example.myapplication;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class CreatePdf {
    public static void main(String[] args) {
        // Specify the path of the PDF file
        String dest = "sample.pdf";

        try {
            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(dest);

            // Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);

            // Initialize document
            Document document = new Document(pdf);

            // Add paragraph to the document
            document.add(new Paragraph("Hello, World!"));

            // Close document
            document.close();

            System.out.println("PDF Created Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}