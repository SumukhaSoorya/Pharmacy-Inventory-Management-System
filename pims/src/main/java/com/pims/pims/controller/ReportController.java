package com.pims.pims.controller;

import com.pims.pims.repository.BillRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    private final BillRepository billRepository;

    public ReportController(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @GetMapping("/reports")
    public String reports(Model model) {

        Double sales =
                billRepository.getTotalSales();

        Double gst =
                billRepository.getTotalGST();

        Long bills =
                billRepository.getTotalBills();

        model.addAttribute(
                "sales",
                sales != null ? sales : 0
        );

        model.addAttribute(
                "gst",
                gst != null ? gst : 0
        );

        model.addAttribute(
                "bills",
                bills
        );

        return "reports";
    }
}