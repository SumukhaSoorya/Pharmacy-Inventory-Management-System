package com.pims.pims.service;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;

import com.pims.pims.model.Bill;
import com.pims.pims.model.BillItem;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class PdfService {

    public void generateBillPdf(Bill bill,
                                HttpServletResponse response)
            throws Exception {

        PdfWriter writer =
                new PdfWriter(response.getOutputStream());

        PdfDocument pdf =
                new PdfDocument(writer);

        Document document =
                new Document(pdf);

        document.add(
                new Paragraph("PHARMACY INVENTORY MANAGEMENT SYSTEM")
                        .setBold()
                        .setFontSize(18)
        );

        document.add(
                new Paragraph("Official Tax Invoice")
                        .setFontSize(14)
        );

        document.add(
                new Paragraph("Bill ID: " + bill.getId())
        );

        document.add(
                new Paragraph("Customer: " + bill.getCustomerName())
        );

        document.add(
                new Paragraph("Payment Mode: " + bill.getPaymentMode())
        );

        document.add(
                new Paragraph("Date: " + bill.getBillDate())
        );

        document.add(
                new Paragraph("\n")
        );

        Table table =
                new Table(5);

        table.addCell("Medicine");
        table.addCell("Qty");
        table.addCell("Price");
        table.addCell("GST");
        table.addCell("Total");

        for (BillItem item : bill.getItems()) {

            table.addCell(item.getMedicineName());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.valueOf(item.getUnitPrice()));
            table.addCell(String.valueOf(item.getGst()));
            table.addCell(String.valueOf(item.getTotalPrice()));
        }

        document.add(table);

        document.add(
                new Paragraph("\nGST Amount: ₹" + bill.getGstAmount())
        );

        document.add(
                new Paragraph("Discount: ₹" + bill.getDiscount())
        );

        document.add(
                new Paragraph("Grand Total: ₹" + bill.getTotalAmount())
                        .setBold()
                        .setFontSize(16)
        );

        document.add(
                new Paragraph("\nThank you for shopping with us.")
        );

        document.close();
    }
}