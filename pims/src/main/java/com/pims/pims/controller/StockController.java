package com.pims.pims.controller;

import com.pims.pims.model.Stock;
import com.pims.pims.service.StockService;
import com.pims.pims.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;
    private final MedicineService medicineService;

    public StockController(StockService stockService, MedicineService medicineService) {
        this.stockService = stockService;
        this.medicineService = medicineService;
    }

    @GetMapping
    public String view(Model model) {
        model.addAttribute("stockList", stockService.getAll());
        model.addAttribute("medicines", medicineService.getAll());
        model.addAttribute("stock", new Stock());
        return "stock";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Stock stock) {
        stockService.save(stock);
        return "redirect:/stock";
    }
}