package com.itgeticaret.controllers;



import com.itgeticaret.models.PurchaseOrder;
import com.itgeticaret.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<PurchaseOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public PurchaseOrder getOrderById(@PathVariable long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public void createOrder(@RequestBody PurchaseOrder purchaseOrder) {
        orderService.createOrder(purchaseOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable long id, @RequestBody PurchaseOrder purchaseOrder) {
        try {
            orderService.updateOrder(id, purchaseOrder);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Data updated in the database", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
    }
}
