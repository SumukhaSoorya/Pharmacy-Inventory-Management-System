package com.pims.pims.controller;

import com.pims.pims.model.Medicine;
import com.pims.pims.model.Stock;
import com.pims.pims.service.MedicineService;
import com.pims.pims.service.StockService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;
    private final MedicineService medicineService;

    public StockController(StockService stockService,
                           MedicineService medicineService) {
        this.stockService = stockService;
        this.medicineService = medicineService;
    }

    @GetMapping
    public String stockPage(Model model,
                            @RequestParam(required = false) String success,
                            @RequestParam(required = false) String error) {

        model.addAttribute("stockList", stockService.getAll());
        model.addAttribute("medicines", medicineService.getAll());

        model.addAttribute("success", success);
        model.addAttribute("error", error);

        return "stock";
    }

    @PostMapping("/save")
    public String saveStock(@RequestParam("medicineId") Long medicineId,
                            @RequestParam("quantity") int quantity,
                            @RequestParam("reorderLevel") int reorderLevel,
                            @RequestParam("expiryDate") String expiryDate) {

        Medicine medicine = medicineService.getById(medicineId);

        if (medicine == null) {
            return "redirect:/stock?error=medicine-not-found";
        }

        Stock stock = new Stock();
        stock.setMedicine(medicine);
        stock.setQuantity(quantity);
        stock.setReorderLevel(reorderLevel);
        stock.setExpiryDate(LocalDate.parse(expiryDate));

        stockService.save(stock);

        return "redirect:/stock?success=stock-added";
    }
}