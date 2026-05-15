package com.pims.pims.controller;

import com.pims.pims.service.PurchaseOrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/factory")
public class FactoryController {

    private final PurchaseOrderService poService;

    public FactoryController(PurchaseOrderService poService) {
        this.poService = poService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("orders", poService.getAll());

        return "factory-dashboard";
    }

    @PostMapping("/orders/{id}/accept")
    public String acceptOrder(@PathVariable Long id) {

        poService.updateStatusWithRemark(
                id,
                "Accepted",
                "Order accepted by factory"
        );

        return "redirect:/factory/dashboard";
    }

    @PostMapping("/orders/{id}/reject")
    public String rejectOrder(@PathVariable Long id,
                              @RequestParam(required = false) String remark) {

        if (remark == null || remark.isBlank()) {
            remark = "Order rejected by factory";
        }

        poService.updateStatusWithRemark(
                id,
                "Rejected",
                remark
        );

        return "redirect:/factory/dashboard";
    }

    @PostMapping("/orders/{id}/dispatch")
    public String dispatchOrder(@PathVariable Long id) {

        poService.updateStatusWithRemark(
                id,
                "Dispatched",
                "Order dispatched by factory"
        );

        return "redirect:/factory/dashboard";
    }
}