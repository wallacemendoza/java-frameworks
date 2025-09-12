package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.service.PartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddPartController {

    private final PartService partService;

    public AddPartController(PartService partService) {
        this.partService = partService;
    }

    // ✅ Show Inhouse form
    @GetMapping("/showFormForAddInhousePart")
    public String showFormForAddInhousePart(Model model) {
        model.addAttribute("inhousePart", new InhousePart());
        model.addAttribute("title", "Add Inhouse Component");
        return "InhousePartForm";
    }

    // ✅ Save Inhouse part
    @PostMapping("/saveInhousePart")
    public String saveInhousePart(@ModelAttribute("inhousePart") InhousePart inhousePart) {
        partService.save(inhousePart);
        return "redirect:/mainscreen";
    }

    // ✅ Show Outsourced form
    @GetMapping("/showFormForAddOutsourcedPart")
    public String showFormForAddOutsourcedPart(Model model) {
        model.addAttribute("outsourcedPart", new OutsourcedPart());
        model.addAttribute("title", "Add Outsourced Component");
        return "OutsourcedPartForm";
    }

    // ✅ Save Outsourced part
    @PostMapping("/saveOutsourcedPart")
    public String saveOutsourcedPart(@ModelAttribute("outsourcedPart") OutsourcedPart outsourcedPart) {
        partService.save(outsourcedPart);
        return "redirect:/mainscreen";
    }
}
