package com.pims.pims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pims.pims.model.PurchaseOrder;
import com.pims.pims.service.PurchaseOrderService;
import com.pims.pims.service.SupplierService;

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
    @PostMapping("/{id}/received")
public String markReceived(
        @PathVariable Long id
) {

    poService.updateStatus(
            id,
            "Received"
    );

    return "redirect:/purchase-orders";
}
}