package com.pims.pims.controller;

import com.pims.pims.model.Supplier;
import com.pims.pims.service.SupplierService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    // PAGE
    @GetMapping
    public String supplierPage(Model model) {

        model.addAttribute(
                "suppliers",
                service.getAll()
        );

        model.addAttribute(
                "supplier",
                new Supplier()
        );

        return "suppliers";
    }

    // SAVE
    @PostMapping("/save")
    public String save(
            @ModelAttribute Supplier supplier
    ) {

        service.save(supplier);

        return "redirect:/suppliers";
    }
}