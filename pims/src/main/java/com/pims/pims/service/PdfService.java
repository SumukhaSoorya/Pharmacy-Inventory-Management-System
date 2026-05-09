package com.pims.pims.service;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class PdfService {

    public void generateBillPdf() throws FileNotFoundException {

        String path = "bill.pdf";

        PdfWriter writer =
                new PdfWriter(path);

        PdfDocument pdf =
                new PdfDocument(writer);

        Document document =
                new Document(pdf);

        // TITLE
        document.add(
                new Paragraph("Pharmacy Invoice")
                .setBold()
                .setFontSize(22)
        );

        document.add(
                new Paragraph(
                        "Pharmacy Inventory Management System"
                )
        );

        // TABLE
        Table table =
                new Table(4);

        table.addCell("Medicine");
        table.addCell("Qty");
        table.addCell("Price");
        table.addCell("Total");

        table.addCell("Paracetamol");
        table.addCell("2");
        table.addCell("50");
        table.addCell("100");

        document.add(table);

        // TOTAL
        document.add(
                new Paragraph(
                        "\nGrand Total: ₹100"
                ).setBold()
        );

        document.close();

        System.out.println("PDF Generated!");
    }
}