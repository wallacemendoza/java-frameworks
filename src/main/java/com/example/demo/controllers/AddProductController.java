package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    // ✅ Show Add Product form
    @GetMapping("/showFormForAddProduct")
    public String showFormForAddProduct(Model model) {
        Product product = new Product();
        product.setParts(new HashSet<>());
        model.addAttribute("product", product);
        model.addAttribute("allParts", partService.findAll());
        model.addAttribute("title", "Add PC Build");
        return "productForm";
    }

    // ✅ Save Product (Add + Update) with validation
    @PostMapping("/saveProduct")
    public String saveProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam(value = "selectedParts", required = false) List<Long> selectedParts,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("allParts", partService.findAll());
            model.addAttribute("title", (product.getId() == 0 ? "Add PC Build" : "Update PC Build"));
            return "productForm";
        }

        Set<Part> parts = new HashSet<>();
        if (selectedParts != null) {
            for (Long partId : selectedParts) {
                Part p = partService.findById(Math.toIntExact(partId));
                if (p != null) {
                    parts.add(p);
                }
            }
        }
        product.setParts(parts);
        productService.save(product);

        return "redirect:/mainscreen";
    }

    // ✅ Buy Now functionality with confirmation page
    @GetMapping("/buyNow/{id}")
    public String buyNow(@PathVariable("id") long id, Model model) {
        Product product = productService.findById((int) id);

        if (product != null) {
            if (product.getInv() > 0) {
                product.setInv(product.getInv() - 1); // decrease inventory
                productService.save(product);
                model.addAttribute("successMessage", product.getName() + " purchased successfully!");
            } else {
                model.addAttribute("errorMessage", "Product is out of stock!");
            }
            model.addAttribute("product", product);
        } else {
            model.addAttribute("errorMessage", "Product not found!");
        }

        return "buyNowConfirmation"; // ✅ points to new template
    }

    // ✅ Show Update Product form
    @GetMapping("/showFormForUpdateProduct/{id}")
    public String showFormForUpdateProduct(@PathVariable("id") long id, Model model) {
        Product product = productService.findById((int) id);

        if (product == null) {
            return "redirect:/mainscreen";
        }
        if (product.getParts() == null) {
            product.setParts(new HashSet<>());
        }

        model.addAttribute("product", product);
        model.addAttribute("allParts", partService.findAll());
        model.addAttribute("title", "Update PC Build");
        return "productForm";
    }

    // ✅ Delete Product
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        productService.deleteById((int) id);
        return "redirect:/mainscreen";
    }
}
