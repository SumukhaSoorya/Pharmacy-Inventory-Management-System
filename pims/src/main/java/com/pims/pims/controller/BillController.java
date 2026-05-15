package com.pims.pims.controller;

import com.pims.pims.dto.BillRequest;
import com.pims.pims.model.Bill;
import com.pims.pims.service.BillService;
import com.pims.pims.service.MedicineService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/billing")
public class BillController {

    private final MedicineService medicineService;
    private final BillService billService;

    public BillController(MedicineService medicineService,
                          BillService billService) {
        this.medicineService = medicineService;
        this.billService = billService;
    }

    @GetMapping
    public String billingPage(Model model) {
        model.addAttribute("medicines", medicineService.getAll());
        return "billing";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<?> saveBill(@RequestBody BillRequest request) {

        try {
            Bill bill = billService.createBill(request);
            return ResponseEntity.ok(bill);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}