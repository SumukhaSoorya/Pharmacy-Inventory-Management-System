package com.pims.pims.controller;

import com.pims.pims.dto.BillRequest;
import com.pims.pims.model.Bill;
import com.pims.pims.service.BillService;
import com.pims.pims.service.MedicineService;
import com.pims.pims.service.PdfService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/billing")
public class BillController {

    private final MedicineService medicineService;
    private final BillService billService;
    private final PdfService pdfService;

    public BillController(MedicineService medicineService,
                          BillService billService,
                          PdfService pdfService) {

        this.medicineService = medicineService;
        this.billService = billService;
        this.pdfService = pdfService;
    }

    @GetMapping
    public String billingPage(Model model) {

        model.addAttribute("medicines", medicineService.getAll());

        return "billing";
    }

    @PostMapping("/save")
    @ResponseBody
    public Long saveBill(@RequestBody BillRequest request) {

        Bill bill = billService.createBill(request);

        return bill.getId();
    }

    @GetMapping("/pdf/{id}")
    public void downloadPdf(@PathVariable Long id,
                            HttpServletResponse response) {

        try {

            response.setContentType("application/pdf");

            response.setHeader(
                    "Content-Disposition",
                    "attachment; filename=bill-" + id + ".pdf"
            );

            Bill bill = billService.getBill(id);

            pdfService.generateBillPdf(bill, response);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}