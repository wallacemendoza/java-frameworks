package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.service.PartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    // ✅ Save Inhouse part (with validation)
    @PostMapping("/saveInhousePart")
    public String saveInhousePart(@Valid @ModelAttribute("inhousePart") InhousePart inhousePart,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Add Inhouse Component");
            return "InhousePartForm"; // redisplay with validation errors
        }
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

    // ✅ Save Outsourced part (with validation)
    @PostMapping("/saveOutsourcedPart")
    public String saveOutsourcedPart(@Valid @ModelAttribute("outsourcedPart") OutsourcedPart outsourcedPart,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Add Outsourced Component");
            return "OutsourcedPartForm"; // redisplay with validation errors
        }
        partService.save(outsourcedPart);
        return "redirect:/mainscreen";
    }

    // ✅ Show Update form (handles both types of parts)
    @GetMapping("/showFormForUpdatePart/{id}")
    public String showFormForUpdatePart(@PathVariable("id") long id, Model model) {
        Part part = partService.findById((int) id);

        if (part instanceof InhousePart) {
            model.addAttribute("inhousePart", part);
            model.addAttribute("title", "Update Inhouse Component");
            return "InhousePartForm";
        } else if (part instanceof OutsourcedPart) {
            model.addAttribute("outsourcedPart", part);
            model.addAttribute("title", "Update Outsourced Component");
            return "OutsourcedPartForm";
        } else {
            return "redirect:/mainscreen"; // fallback if not found
        }
    }
}
