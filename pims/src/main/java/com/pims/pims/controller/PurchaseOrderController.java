package com.pims.pims.controller;

import com.pims.pims.model.PurchaseOrder;
import com.pims.pims.model.Stock;
import com.pims.pims.model.Supplier;
import com.pims.pims.service.PurchaseOrderService;
import com.pims.pims.service.StockService;
import com.pims.pims.service.SupplierService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
                                 @RequestParam(required = false) String error,
                                 @RequestParam(required = false) String autoCreated) {

        model.addAttribute("orders", purchaseOrderService.getAll());
        model.addAttribute("suppliers", supplierService.getAll());

        if (success != null) {
            model.addAttribute("success", true);
        }

        if (error != null) {
            model.addAttribute("error", true);
        }

        if (autoCreated != null) {
            model.addAttribute("autoCreated", autoCreated);
        }

        return "purchase-orders";
    }

    @PostMapping("/save")
    public String savePurchaseOrder(@RequestParam Long supplierId,
                                    @RequestParam String medicineName,
                                    @RequestParam Integer quantity,
                                    @RequestParam Double totalAmount) {

        Supplier supplier = supplierService.getById(supplierId);

        if (supplier == null) {
            return "redirect:/purchase-orders?error=true";
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setMedicineName(medicineName);
        purchaseOrder.setQuantity(quantity);
        purchaseOrder.setTotalAmount(totalAmount);
        purchaseOrder.setStatus("Pending");

        purchaseOrderService.save(purchaseOrder);

        return "redirect:/purchase-orders?success=true";
    }

    @PostMapping("/auto-reorder")
    public String autoReorder() {

        List<Stock> lowStockItems = stockService.getLowStock();
        List<Supplier> suppliers = supplierService.getAll();

        if (lowStockItems == null || lowStockItems.isEmpty()) {
            return "redirect:/purchase-orders?autoCreated=0";
        }

        if (suppliers == null || suppliers.isEmpty()) {
            return "redirect:/purchase-orders?error=true";
        }

        Supplier defaultSupplier = suppliers.get(0);

        int createdCount = 0;

        for (Stock stock : lowStockItems) {

            if (stock == null || stock.getMedicine() == null) {
                continue;
            }

            String medicineName = stock.getMedicine().getName();

            if (medicineName == null || medicineName.isBlank()) {
                continue;
            }

            int reorderLevel = stock.getReorderLevel();
            int currentQuantity = stock.getQuantity();

            int orderQuantity = reorderLevel - currentQuantity;

            if (orderQuantity <= 0) {
                orderQuantity = reorderLevel;
            }

            if (orderQuantity <= 0) {
                orderQuantity = 10;
            }

            PurchaseOrder purchaseOrder = new PurchaseOrder();

            purchaseOrder.setSupplier(defaultSupplier);
            purchaseOrder.setMedicineName(medicineName);
            purchaseOrder.setQuantity(orderQuantity);
            purchaseOrder.setTotalAmount(0.0);
            purchaseOrder.setStatus("Pending");

            purchaseOrderService.save(purchaseOrder);

            createdCount++;
        }

        return "redirect:/purchase-orders?autoCreated=" + createdCount;
    }

    @PostMapping("/{id}/received")
    public String markReceived(@PathVariable Long id) {

        purchaseOrderService.updateStatus(id, "Received");

        return "redirect:/purchase-orders";
    }

    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id) {

        purchaseOrderService.updateStatus(id, "Cancelled");

        return "redirect:/purchase-orders";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {

        purchaseOrderService.deleteById(id);

        return "redirect:/purchase-orders";
    }
}