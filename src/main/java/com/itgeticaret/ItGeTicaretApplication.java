package com.itgeticaret;

import com.itgeticaret.models.User;
import com.itgeticaret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItGeTicaretApplication implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public ItGeTicaretApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ItGeTicaretApplication.class, args);
    }


    public void run(String... args) {
        if (userService.getAllUsers().isEmpty()) {
            // Create a default admin account if the database is empty
            User adminUser = new User();
            userService.createAdminUser(adminUser);
        }

    }






}
