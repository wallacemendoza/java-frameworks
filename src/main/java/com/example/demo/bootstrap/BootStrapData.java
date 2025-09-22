package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (partRepository.count() == 0 && productRepository.count() == 0) {
            // ✅ Create Inhouse parts
            InhousePart cpu = new InhousePart("Intel i9 CPU", 500.0, 10, 1, 50, 1001);
            InhousePart ram = new InhousePart("Corsair 16GB RAM", 150.0, 20, 1, 100, 1002);
            InhousePart mobo = new InhousePart("ASUS Motherboard", 250.0, 15, 1, 40, 1003);

            // ✅ Create Outsourced parts
            OutsourcedPart gpu = new OutsourcedPart("NVIDIA RTX 3080", 1200.0, 5, 1, 20, "NVIDIA");
            OutsourcedPart psu = new OutsourcedPart("EVGA 750W PSU", 100.0, 25, 1, 60, "EVGA");

            // Save parts
            partRepository.save(cpu);
            partRepository.save(ram);
            partRepository.save(mobo);
            partRepository.save(gpu);
            partRepository.save(psu);

            // ✅ Reload managed entities
            List<Part> managedParts = (List<Part>) partRepository.findAll();

            // ✅ Create 5 sample products
            Product gamingPc = new Product("Gaming PC", 2200.0, 5, 1, 10);
            Product workstationPc = new Product("Workstation PC", 3000.0, 3, 1, 6);
            Product budgetPc = new Product("Budget Build", 800.0, 8, 1, 15);
            Product streamingPc = new Product("Streaming PC", 1500.0, 4, 1, 7);
            Product renderingRig = new Product("Rendering Rig", 3500.0, 2, 1, 5);

            // Attach all parts to each product
            for (Part p : managedParts) {
                gamingPc.addPart(p);
                workstationPc.addPart(p);
                budgetPc.addPart(p);
                streamingPc.addPart(p);
                renderingRig.addPart(p);
            }

            // Save products
            productRepository.save(gamingPc);
            productRepository.save(workstationPc);
            productRepository.save(budgetPc);
            productRepository.save(streamingPc);
            productRepository.save(renderingRig);

            System.out.println("✅ Seeded database with 5 products and parts.");
        } else {
            System.out.println("⚠️ Existing data found, skipping bootstrap.");
        }
    }
}
