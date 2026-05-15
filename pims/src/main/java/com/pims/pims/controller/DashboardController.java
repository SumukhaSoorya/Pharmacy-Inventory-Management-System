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

        Long lowStockCount = stockService.getLowStockCount();
        Long expiredCount = stockService.getExpiredCount();
        Long expiringSoonCount = stockService.getExpiringSoonCount();

        long totalAlerts =
                lowStockCount + expiredCount + expiringSoonCount;

        model.addAttribute("medicineCount", medicineService.getAll().size());
        model.addAttribute("availableStock", stockService.getAvailableStock());

        model.addAttribute("lowStockCount", lowStockCount);
        model.addAttribute("expiredCount", expiredCount);
        model.addAttribute("expiringSoonCount", expiringSoonCount);
        model.addAttribute("totalAlerts", totalAlerts);

        model.addAttribute("pendingOrders", purchaseOrderService.countByStatus("Pending"));

        model.addAttribute("totalSales", billService.getTotalSales());
        model.addAttribute("totalBills", billService.getTotalBills());
        model.addAttribute("totalGST", billService.getTotalGST());

        model.addAttribute("stockList", stockService.getAll());
        model.addAttribute("expiredStock", stockService.getExpiredStock());
        model.addAttribute("expiringSoonStock", stockService.getExpiringSoon());
        model.addAttribute("lowStockList", stockService.getLowStock());

        return "dashboard";
    }
}