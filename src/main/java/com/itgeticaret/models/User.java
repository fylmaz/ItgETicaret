package com.itgeticaret.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String password;
    private String email;

    private String activationCode;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    private boolean active;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "basket_id")
    @JsonIgnoreProperties("user")
    private Basket basket;


    public User(long id, String password, String email, List<PurchaseOrder> purchaseOrders) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.purchaseOrders = purchaseOrders;
        this.basket = new Basket();
    }
}
