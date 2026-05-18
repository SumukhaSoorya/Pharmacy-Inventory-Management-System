package com.pims.pims.controller;

import com.pims.pims.service.PurchaseOrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/factory")
public class FactoryController {

    private final PurchaseOrderService purchaseOrderService;

    public FactoryController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("orders", purchaseOrderService.getAll());

        return "factory-dashboard";
    }

    @PostMapping("/orders/{id}/accept")
    public String acceptOrder(@PathVariable Long id) {

        purchaseOrderService.updateStatus(id, "Accepted");

        return "redirect:/factory/dashboard";
    }

    @PostMapping("/orders/{id}/reject")
    public String rejectOrder(@PathVariable Long id) {

        purchaseOrderService.updateStatus(id, "Rejected");

        return "redirect:/factory/dashboard";
    }

    @PostMapping("/orders/{id}/dispatch")
    public String dispatchOrder(@PathVariable Long id) {

        purchaseOrderService.updateStatus(id, "Dispatched");

        return "redirect:/factory/dashboard";
    }
}