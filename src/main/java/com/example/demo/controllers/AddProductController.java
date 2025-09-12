package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AddProductController {

    private final ProductService productService;
    private final PartService partService;

    public AddProductController(ProductService productService, PartService partService) {
        this.productService = productService;
        this.partService = partService;
    }

    // Show Add Product form
    @GetMapping("/showFormForAddProduct")
    public String showFormForAddProduct(Model model) {
        model.addAttribute("product", new Product());
        // If your PartService doesn't have findAll(), use: partService.listAll(null)
        model.addAttribute("allParts", partService.findAll());
        model.addAttribute("title", "Add PC Build");
        return "productForm";
    }

    // Save Product (Add + Update)
    @PostMapping("/saveProduct")
    public String saveProduct(
            @ModelAttribute("product") Product product,
            @RequestParam(value = "selectedParts", required = false) List<Long> selectedParts
    ) {
        Set<Part> parts = new HashSet<>();
        if (selectedParts != null) {
            for (Long partId : selectedParts) {
                int idAsInt = Math.toIntExact(partId); // convert Long -> int for your service
                Part p = partService.findById(idAsInt);
                if (p != null) {
                    parts.add(p);
                }
            }
        }
        product.setParts(parts);
        productService.save(product);
        return "redirect:/mainscreen";
    }

    // Show Update Product form
    @GetMapping("/showFormForUpdateProduct/{id}")
    public String showFormForUpdateProduct(@PathVariable("id") long id, Model model) {
        Product product = productService.findById((int) id);
        model.addAttribute("product", product);
        model.addAttribute("allParts", partService.findAll()); // or listAll(null)
        model.addAttribute("title", "Update PC Build");
        return "productForm";
    }

    // Delete Product
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        productService.deleteById((int) id);
        return "redirect:/mainscreen";
    }
}
