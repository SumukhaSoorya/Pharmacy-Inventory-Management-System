package com.pims.pims.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.pims.pims.service.CsvService;
import jakarta.servlet.http.HttpServletResponse;
import com.pims.pims.model.Medicine;
import com.pims.pims.service.MedicineService;
import java.io.IOException;

@Controller
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineService service;
    private final CsvService csvService;

   public MedicineController(MedicineService service,
                          CsvService csvService) {

    this.service = service;
    this.csvService = csvService;
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
    @GetMapping("/export")
public void exportCsv(
        HttpServletResponse response
) throws IOException {

    csvService.exportMedicines(response);
}
}