package com.itgeticaret.controllers;

import com.itgeticaret.models.Product;
import com.itgeticaret.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/main")
public class MainPageController {

    private final ProductService productService;

    @Autowired
    public MainPageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String mainPage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "mainPage.html";
    }
}
