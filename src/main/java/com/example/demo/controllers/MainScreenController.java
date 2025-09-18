package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainScreenController {

    private final PartService partService;
    private final ProductService productService;

    public MainScreenController(PartService partService, ProductService productService) {
        this.partService = partService;
        this.productService = productService;
    }

    // ✅ Show the main inventory management screen
    @GetMapping({"/", "/mainscreen"})
    public String listPartsAndProducts(Model model) {
        List<Part> parts = partService.findAll();
        List<Product> products = productService.findAll();

        model.addAttribute("parts", parts);
        model.addAttribute("products", products);

        return "mainscreen";
    }

    // ✅ Show the About Us page
    @GetMapping("/about")
    public String aboutPage() {
        return "about";  // Loads about.html from templates folder
    }

    // ⚠️ Removed duplicate /showFormForAddProduct mapping
    // That route is now ONLY in AddProductController
}
