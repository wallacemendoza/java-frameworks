package com.example.demo;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(PartService partService, ProductService productService) {
        return args -> {
            List<Part> existingParts = partService.findAll();
            List<Product> existingProducts = productService.findAll();

            if (existingParts.isEmpty() && existingProducts.isEmpty()) {
                // ✅ Create Inhouse parts
                InhousePart cpu = new InhousePart("Intel i9 CPU", 500.0, 10, 1, 50, 1001);
                InhousePart ram = new InhousePart("Corsair 16GB RAM", 150.0, 20, 1, 100, 1002);
                InhousePart mobo = new InhousePart("ASUS Motherboard", 250.0, 15, 1, 40, 1003);

                // ✅ Create Outsourced parts
                OutsourcedPart gpu = new OutsourcedPart("NVIDIA RTX 3080", 1200.0, 5, 1, 20, "NVIDIA");
                OutsourcedPart psu = new OutsourcedPart("EVGA 750W PSU", 100.0, 25, 1, 60, "EVGA");

                // Save parts
                partService.save(cpu);
                partService.save(ram);
                partService.save(mobo);
                partService.save(gpu);
                partService.save(psu);

                // ✅ Reload from DB so they’re managed entities
                List<Part> managedParts = partService.findAll();

                // ✅ Create a valid Product (Gaming PC)
                Product gamingPc = new Product("Gaming PC", 2200.0, 5, 1, 10);
                // inv = 5, minInv = 1, maxInv = 10 → passes validation

                // Attach saved parts
                for (Part p : managedParts) {
                    gamingPc.addPart(p);
                }

                // Save product
                productService.save(gamingPc);

                System.out.println("✅ Seeded database with parts and product.");
            } else {
                System.out.println("⚠️ Existing data found, skipping bootstrap.");
            }
        };
    }
}
