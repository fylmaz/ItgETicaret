package com.itgeticaret.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class PurchaseOrder {    //This class' name is Order in normal. However while making operations about this class at database,
                                //since 'order' is a reserve word of SQL, it creates some problems. Thats why this model class' name is PurchaseOrder
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_amount")
    private double totalAmount;

    @ManyToMany
    private List<Product> products = new ArrayList<>();
}
