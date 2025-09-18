package com.example.demo.domain;

import com.example.demo.validators.ValidInventory;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "products")
@ValidInventory // ✅ ensures inventory validation like Part
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Min(value = 0, message = "Price value must be positive")
    private double price;

    @Min(value = 0, message = "Inventory value must be positive")
    private int inv;

    @Min(value = 0, message = "Minimum inventory must be positive")
    private Integer minInv;

    @Min(value = 0, message = "Maximum inventory must be positive")
    private Integer maxInv;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_part",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "part_id"))
    private Set<Part> parts = new HashSet<>();

    // ----- Constructors -----
    public Product() {}

    public Product(String name, double price, int inv, Integer minInv, Integer maxInv) {
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.minInv = minInv;
        this.maxInv = maxInv;
    }

    public Product(long id, String name, double price, int inv, Integer minInv, Integer maxInv) {
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

    public int getInv() { return inv; }
    public void setInv(int inv) { this.inv = inv; }

    public Integer getMinInv() { return minInv; }
    public void setMinInv(Integer minInv) { this.minInv = minInv; }

    public Integer getMaxInv() { return maxInv; }
    public void setMaxInv(Integer maxInv) { this.maxInv = maxInv; }

    public Set<Part> getParts() { return parts; }
    public void setParts(Set<Part> parts) { this.parts = parts; }

    // ----- Utility Methods -----
    public void addPart(Part part) {
        this.parts.add(part);
        part.getProducts().add(this);
    }

    public void removePart(Part part) {
        this.parts.remove(part);
        part.getProducts().remove(this);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
