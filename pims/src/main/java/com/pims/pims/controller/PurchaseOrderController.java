package com.pims.pims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pims.pims.service.PurchaseOrderService;
import com.pims.pims.service.StockService;
import com.pims.pims.service.SupplierService;

@Controller
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;
    private final SupplierService supplierService;
    private final StockService stockService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService,
                                   SupplierService supplierService,
                                   StockService stockService) {
        this.purchaseOrderService = purchaseOrderService;
        this.supplierService = supplierService;
        this.stockService = stockService;
    }

    @GetMapping
    public String purchaseOrders(Model model,
                                 @RequestParam(required = false) String success,
                                 @RequestParam(required = false) String autoCreated) {

        model.addAttribute("orders", purchaseOrderService.getAll());
        model.addAttribute("suppliers", supplierService.getAll());
        model.addAttribute("success", success);
        model.addAttribute("autoCreated", autoCreated);

        return "purchase-orders";
    }

    @PostMapping("/save")
    public String saveOrder(@RequestParam Long supplierId,
                            @RequestParam String medicineName,
                            @RequestParam int quantity,
                            @RequestParam double totalAmount) {

        purchaseOrderService.createOrder(
                supplierId,
                medicineName,
                quantity,
                totalAmount
        );

        return "redirect:/purchase-orders?success=order-placed";
    }

    @PostMapping("/auto-reorder")
    public String autoReorder() {

        int created =
                purchaseOrderService.autoCreateReorderDrafts(
                        stockService.getLowStock()
                );

        return "redirect:/purchase-orders?autoCreated=" + created;
    }
}