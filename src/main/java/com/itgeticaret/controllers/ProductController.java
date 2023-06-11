package com.itgeticaret.controllers;



import com.itgeticaret.enums.Gender;
import com.itgeticaret.models.Product;
import com.itgeticaret.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable long id, @RequestBody Product product) {
        try {
            productService.updateProduct(id, product);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Data updated in the database", HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/filter")
    public List<Product> getFilteredProducts(@RequestParam("gender") String gender) {
        Gender genderEnum;
        if (gender == null || gender.isEmpty()) {
            genderEnum = null;
        } else {
            genderEnum = Gender.valueOf(gender.toUpperCase(Locale.forLanguageTag("EN")));
        }
        return productService.findByGender(genderEnum);
    }

}

