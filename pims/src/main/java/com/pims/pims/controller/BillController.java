package com.pims.pims.controller;

import com.pims.pims.model.Bill;
import com.pims.pims.service.BillService;
import com.pims.pims.service.MedicineService;
import com.pims.pims.service.PdfService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/billing")
public class BillController {

    private final MedicineService medicineService;

    private final BillService billService;

    private final PdfService pdfService;

    // CONSTRUCTOR
    public BillController(MedicineService medicineService,
                          BillService billService,
                          PdfService pdfService) {

        this.medicineService = medicineService;
        this.billService = billService;
        this.pdfService = pdfService;
    }

    // BILLING PAGE
    @GetMapping
    public String billingPage(Model model) {

        model.addAttribute(
                "medicines",
                medicineService.getAll()
        );

        return "billing";
    }

    // SAVE BILL
    @PostMapping("/save")
    public String saveBill(@ModelAttribute Bill bill) {

        billService.save(bill);

        return "redirect:/dashboard";
    }

    // GENERATE PDF
    @GetMapping("/generate-pdf")
    @ResponseBody
    public String generatePdf() {

        try {

            pdfService.generateBillPdf();

            return "✅ PDF Generated Successfully!";

        } catch (Exception e) {

            return "❌ Error: " + e.getMessage();
        }
    }
}