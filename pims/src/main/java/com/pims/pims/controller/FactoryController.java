package com.pims.pims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pims.pims.service.PurchaseOrderService;

@Controller
@RequestMapping("/factory")
public class FactoryController {

    private final PurchaseOrderService poService;

    public FactoryController(
            PurchaseOrderService poService
    ) {

        this.poService = poService;
    }
    @PostMapping("/orders/{id}/dispatch")
public String markDispatched(
        @PathVariable Long id
) {

    poService.updateStatus(
            id,
            "Dispatched"
    );

    return "redirect:/factory/dashboard";
}

    // DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(Model model){

        model.addAttribute(
                "orders",
                poService.getAll()
        );

        return "factory-dashboard";
    }
}