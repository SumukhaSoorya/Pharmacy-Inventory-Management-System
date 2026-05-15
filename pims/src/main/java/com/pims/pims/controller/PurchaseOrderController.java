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

    private final PurchaseOrderService poService;
    private final SupplierService supplierService;

    public PurchaseOrderController(PurchaseOrderService poService,
                                   SupplierService supplierService) {
        this.poService = poService;
        this.supplierService = supplierService;
    }

    @GetMapping
    public String page(Model model,
                       @RequestParam(required = false) String success,
                       @RequestParam(required = false) String error) {

        model.addAttribute("orders", poService.getAll());
        model.addAttribute("suppliers", supplierService.getAll());

        model.addAttribute("success", success);
        model.addAttribute("error", error);

        return "purchase-orders";
    }

    @PostMapping("/save")
    public String save(@RequestParam("supplierId") Long supplierId,
                       @RequestParam("medicineName") String medicineName,
                       @RequestParam("quantity") int quantity,
                       @RequestParam("totalAmount") double totalAmount) {

        Supplier supplier = supplierService.getById(supplierId);

        if (supplier == null) {
            return "redirect:/purchase-orders?error=supplier-not-found";
        }

        PurchaseOrder order = new PurchaseOrder();

        order.setSupplier(supplier);
        order.setMedicineName(medicineName);
        order.setQuantity(quantity);
        order.setTotalAmount(totalAmount);
        order.setStatus("Pending");

        poService.save(order);

        return "redirect:/purchase-orders?success=order-placed";
    }

    @PostMapping("/{id}/received")
    public String markReceived(@PathVariable Long id) {

        poService.updateStatus(id, "Received");

        return "redirect:/purchase-orders";
    }
}