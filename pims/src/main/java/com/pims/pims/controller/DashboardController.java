package com.pims.pims.controller;

import com.pims.pims.service.BillService;
import com.pims.pims.service.MedicineService;
import com.pims.pims.service.StockService;
import com.pims.pims.service.PurchaseOrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final MedicineService medicineService;
    private final StockService stockService;
    private final BillService billService;
    private final PurchaseOrderService purchaseOrderService;

    public DashboardController(MedicineService medicineService,
                               StockService stockService,
                               BillService billService,
                               PurchaseOrderService purchaseOrderService) {

        this.medicineService = medicineService;
        this.stockService = stockService;
        this.billService = billService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("medicineCount", medicineService.getAll().size());

        model.addAttribute("availableStock", stockService.getAvailableStock());

        model.addAttribute("lowStockCount", stockService.getLowStockCount());

        model.addAttribute("expiredCount", stockService.getExpiredCount());

        model.addAttribute("expiringSoonCount", stockService.getExpiringSoonCount());

        model.addAttribute("pendingOrders", purchaseOrderService.countByStatus("Pending"));

        model.addAttribute("totalSales", billService.getTotalSales());

        model.addAttribute("totalBills", billService.getTotalBills());

        model.addAttribute("stockList", stockService.getAll());

        return "dashboard";
    }
}