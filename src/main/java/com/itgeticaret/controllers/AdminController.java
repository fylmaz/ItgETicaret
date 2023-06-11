package com.itgeticaret.controllers;
import com.itgeticaret.models.Admin;
import com.itgeticaret.models.Product;
import com.itgeticaret.models.User;
import com.itgeticaret.repositories.AdminRepository;
import com.itgeticaret.repositories.ProductRepository;
import com.itgeticaret.repositories.UserRepository;
import com.itgeticaret.enums.Gender;
import com.itgeticaret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final UserService userService;

    @Autowired
    public AdminController(UserRepository userRepository, ProductRepository productRepository,UserService userService) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @GetMapping
    public String adminPage() {
        return "adminPage";
    }

    @GetMapping("/addAdmin")
    public String addAdminPage() {
        return "addAdmin";
    }

    @PostMapping("/addAdmin")
    public String addAdmin(String username, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        User newAdmin = new User();
        userService.createAdminUser(newAdmin);
        newAdmin.setEmail(username);
        newAdmin.setPassword(password);
        userRepository.save(newAdmin);

        return "redirect:/admin";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/addProduct")
    public String addProduct(String name, double price, int stock, String gender) {
        Gender productGender = Gender.valueOf(gender);

        Product product = new Product(name, price, stock, productGender);
        productRepository.save(product);

        return "redirect:/admin";
    }
    @GetMapping("/addProduct")
    public String addProductPage() {
        return "addProduct";
    }
}
