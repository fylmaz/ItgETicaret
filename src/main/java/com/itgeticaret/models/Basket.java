package com.itgeticaret.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "basket", cascade = CascadeType.MERGE)
    private List<Product> products;

    private double totalAmount;

    public Basket(User user) {
        this.user = user;
        this.totalAmount = 0.0;
    }

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
            product.setBasket(this);
        }
    }

    public void removeProduct(Product product) {
        if (product != null) {
            products.remove(product);
            product.setBasket(null);
        }
    }
}
