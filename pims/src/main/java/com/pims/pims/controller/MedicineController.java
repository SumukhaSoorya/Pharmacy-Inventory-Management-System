package com.pims.pims.controller;

import com.pims.pims.model.Medicine;
import com.pims.pims.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineService service;

    public MedicineController(MedicineService service) {
        this.service = service;
    }

    // VIEW PAGE
    @GetMapping
    public String viewPage(Model model) {
        model.addAttribute("medicines", service.getAll());
        model.addAttribute("medicine", new Medicine());
        return "medicines";
    }

    // SAVE
    @PostMapping("/save")
    public String save(@ModelAttribute Medicine medicine) {
        service.save(medicine);
        return "redirect:/medicines";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/medicines";
    }

    // SEARCH
    @GetMapping("/search")
    public String search(@RequestParam String name, Model model) {
        List<Medicine> list = service.search(name);
        model.addAttribute("medicines", list);
        model.addAttribute("medicine", new Medicine());
        return "medicines";
    }
}