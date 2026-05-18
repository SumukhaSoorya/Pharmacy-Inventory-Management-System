package com.pims.pims.controller;

import com.pims.pims.model.PurchaseOrder;
import com.pims.pims.model.Supplier;
import com.pims.pims.service.PurchaseOrderService;
import com.pims.pims.service.SupplierService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;
    private final SupplierService supplierService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService,
                                   SupplierService supplierService) {
        this.purchaseOrderService = purchaseOrderService;
        this.supplierService = supplierService;
    }

    @GetMapping
    public String purchaseOrders(Model model) {

        model.addAttribute("orders", purchaseOrderService.getAll());
        model.addAttribute("suppliers", supplierService.getAll());

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