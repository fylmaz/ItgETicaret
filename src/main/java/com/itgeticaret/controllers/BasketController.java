package com.itgeticaret.controllers;
import com.itgeticaret.config.SpringSecurity;
import com.itgeticaret.models.Basket;
import com.itgeticaret.models.Product;
import com.itgeticaret.models.User;
import com.itgeticaret.services.BasketService;
import com.itgeticaret.services.IBasketService;
import com.itgeticaret.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;
    private final SpringSecurity springSecurity;
    private final ProductService productService;

    public BasketController(BasketService basketService, SpringSecurity springSecurity,ProductService productService) {
        this.basketService = basketService;
        this.springSecurity = springSecurity;
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToBasket(@RequestBody Product product) {
        basketService.addToBasket(product);
        return ResponseEntity.ok("Product added to basket successfully.");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeProductFromBasket(@RequestBody Product product) {
        basketService.removeFromBasket(product);
        return ResponseEntity.ok("Product removed from basket successfully.");
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseBasket() {
        basketService.checkout();
        return ResponseEntity.ok("Purchase completed successfully.");
    }
    @GetMapping
    public ResponseEntity<Basket> getBasket() {
        Basket basket = basketService.getBasket();
        return ResponseEntity.ok(basket);
    }


}

