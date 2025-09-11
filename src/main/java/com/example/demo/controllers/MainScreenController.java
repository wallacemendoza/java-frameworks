package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.domain.InhousePart;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainScreenController {

    private PartService partService;
    private ProductService productService;

    private List<Part> theParts;
    private List<Product> theProducts;

    public MainScreenController(PartService partService, ProductService productService) {
        this.partService = partService;
        this.productService = productService;
    }

    @GetMapping("/mainscreen")
    public String listPartsandProducts(Model theModel,
                                       @Param("partkeyword") String partkeyword,
                                       @Param("productkeyword") String productkeyword) {

        // ✅ Task E: Load sample inventory only if lists are empty
        if (partService.listAll(null).isEmpty() && productService.listAll(null).isEmpty()) {

            // Create PC components using no-arg constructor and setters
            InhousePart cpu = new InhousePart();
            cpu.setName("Intel i9 13900K");
            cpu.setPrice(450.00);
            cpu.setInv(10);
            cpu.setPartId(101);

            InhousePart gpu = new InhousePart();
            gpu.setName("NVIDIA RTX 4080");
            gpu.setPrice(1200.00);
            gpu.setInv(5);
            gpu.setPartId(102);

            InhousePart ssd = new InhousePart();
            ssd.setName("Samsung 980 Pro 2TB");
            ssd.setPrice(180.00);
            ssd.setInv(8);
            ssd.setPartId(103);

            InhousePart ram = new InhousePart();
            ram.setName("Corsair 32GB RAM");
            ram.setPrice(130.00);
            ram.setInv(12);
            ram.setPartId(104);

            InhousePart mobo = new InhousePart();
            mobo.setName("ASUS Z790 Motherboard");
            mobo.setPrice(250.00);
            mobo.setInv(6);
            mobo.setPartId(105);

            // Save components
            partService.save(cpu);
            partService.save(gpu);
            partService.save(ssd);
            partService.save(ram);
            partService.save(mobo);

            // Create a product using no-arg constructor and setters
            Product build1 = new Product();
            build1.setName("Gaming Beast");
            build1.setPrice(2200.00);
            build1.setInv(3);
            build1.addPart(cpu);
            build1.addPart(gpu);
            build1.addPart(ssd);
            build1.addPart(ram);
            build1.addPart(mobo);

            // Save product
            productService.save(build1);
        }

        // Normal page load
        List<Part> partList = partService.listAll(partkeyword);
        theModel.addAttribute("parts", partList);
        theModel.addAttribute("partkeyword", partkeyword);

        List<Product> productList = productService.listAll(productkeyword);
        theModel.addAttribute("products", productList);
        theModel.addAttribute("productkeyword", productkeyword);

        return "mainscreen";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
}
