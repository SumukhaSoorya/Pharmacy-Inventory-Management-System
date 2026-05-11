package com.pims.pims.controller;

import com.pims.pims.model.PurchaseOrder;
import com.pims.pims.service.PurchaseOrderService;
import com.pims.pims.service.SupplierService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService poService;

    private final SupplierService supplierService;

    public PurchaseOrderController(
            PurchaseOrderService poService,
            SupplierService supplierService
    ) {

        this.poService = poService;
        this.supplierService = supplierService;
    }

    // PAGE
    @GetMapping
    public String page(Model model) {

        model.addAttribute(
                "orders",
                poService.getAll()
        );

        model.addAttribute(
                "suppliers",
                supplierService.getAll()
        );

        model.addAttribute(
                "purchaseOrder",
                new PurchaseOrder()
        );

        return "purchase-orders";
    }

    // SAVE
    @PostMapping("/save")
    public String save(
            @ModelAttribute PurchaseOrder po
    ) {

        po.setStatus("Pending");

        poService.save(po);

        return "redirect:/purchase-orders";
    }
}