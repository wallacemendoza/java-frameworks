package com.example.demo.domain;

import com.example.demo.validators.ValidDeletePart;
import com.example.demo.validators.ValidInventory;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@ValidDeletePart
@ValidInventory   // ✅ Enforce min/max/inv validation
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "part_type", discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "Parts")
public abstract class Part implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Min(value = 0, message = "Price must be 0 or greater.")
    private double price;

    @Min(value = 0, message = "Inventory must be 0 or greater.")
    private Integer inv;

    @Min(value = 0, message = "Minimum inventory must be 0 or greater.")
    private Integer minInv;

    @Min(value = 0, message = "Maximum inventory must be 0 or greater.")
    private Integer maxInv;

    @ManyToMany(mappedBy = "parts", fetch = FetchType.EAGER) // ✅ eager avoids LazyInitializationException
    private Set<Product> products = new HashSet<>();

    // ----- Constructors -----
    public Part() {}

    public Part(String name, double price, Integer inv, Integer minInv, Integer maxInv) {
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.minInv = minInv;
        this.maxInv = maxInv;
    }

    public Part(long id, String name, double price, Integer inv, Integer minInv, Integer maxInv) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.minInv = minInv;
        this.maxInv = maxInv;
    }

    // ----- Getters & Setters -----
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Integer getInv() { return inv; }
    public void setInv(Integer inv) { this.inv = inv; }

    public Integer getMinInv() { return minInv; }
    public void setMinInv(Integer minInv) { this.minInv = minInv; }

    public Integer getMaxInv() { return maxInv; }
    public void setMaxInv(Integer maxInv) { this.maxInv = maxInv; }

    public Set<Product> getProducts() { return products; }
    public void setProducts(Set<Product> products) { this.products = products; }

    // ----- Utility Methods -----
    @Override
    public String toString() {
        return this.name; // ✅ matches test expectation
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Part)) return false;
        Part part = (Part) o;
        return id == part.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // ✅ matches test expectation
    }
}
