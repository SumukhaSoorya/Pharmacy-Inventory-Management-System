package com.pims.pims.controller;

import com.pims.pims.service.PurchaseOrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/factory")
public class FactoryController {

    private final PurchaseOrderService poService;

    public FactoryController(
            PurchaseOrderService poService
    ) {

        this.poService = poService;
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