package com.pims.pims.controller;

import com.pims.pims.repository.BillRepository;
import com.pims.pims.service.StockService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    private final BillRepository billRepository;
    private final StockService stockService;

    public ReportController(BillRepository billRepository,
                            StockService stockService) {
        this.billRepository = billRepository;
        this.stockService = stockService;
    }

    @GetMapping("/reports")
    public String reports(Model model) {

        Double totalSales = billRepository.getTotalSales();
        Double totalGst = billRepository.getTotalGST();
        Long totalBills = billRepository.getTotalBills();

        Long cashBills = billRepository.getCashBills();
        Long cardBills = billRepository.getCardBills();
        Long upiBills = billRepository.getUpiBills();

        Long availableStock = stockService.getAvailableStock();
        Long expiredCount = stockService.getExpiredCount();
        Long expiringSoonCount = stockService.getExpiringSoonCount();
        Long lowStockCount = stockService.getLowStockCount();

        model.addAttribute("totalSales", totalSales != null ? totalSales : 0);
        model.addAttribute("totalGst", totalGst != null ? totalGst : 0);
        model.addAttribute("totalBills", totalBills != null ? totalBills : 0);

        model.addAttribute("cashBills", cashBills != null ? cashBills : 0);
        model.addAttribute("cardBills", cardBills != null ? cardBills : 0);
        model.addAttribute("upiBills", upiBills != null ? upiBills : 0);

        model.addAttribute("availableStock", availableStock != null ? availableStock : 0);
        model.addAttribute("expiredCount", expiredCount != null ? expiredCount : 0);
        model.addAttribute("expiringSoonCount", expiringSoonCount != null ? expiringSoonCount : 0);
        model.addAttribute("lowStockCount", lowStockCount != null ? lowStockCount : 0);

        return "reports";
    }
}