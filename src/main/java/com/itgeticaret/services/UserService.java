package com.itgeticaret.services;

import com.itgeticaret.models.Basket;
import com.itgeticaret.models.Role;
import com.itgeticaret.models.User;
import com.itgeticaret.repositories.RoleRepository;
import com.itgeticaret.repositories.UserRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
@Transactional
public class UserService implements IUserService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public User getUserById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("An error occurred - user does not exist");
        }
        return userOptional.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(User user) {
        String activationCode = generateActivationCode(); // Aktivasyon kodu oluşturuluyor
        user.setActivationCode(activationCode); // Kullanıcıya aktivasyon kodu atanıyor
        user.setActive(false); // Kullanıcı aktif olmadığı için active alanı false olarak ayarlanıyor

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);
        }
        user.setRoles(Arrays.asList(role));
        user.setBasket(new Basket());
        user.getBasket().setUser(user);
        userRepository.save(user);
    }


    public void createAdminUser(User adminUser) {
        // Admin kullanıcısının bilgilerini ayarla
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword("admin123");


        String encodedPassword = passwordEncoder.encode(adminUser.getPassword());
        adminUser.setPassword(encodedPassword);


        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }
        adminUser.setRoles(Arrays.asList(adminRole));
        adminUser.setActive(true);
        userRepository.save(adminUser);
    }

    @Override
    public void updateUser(long id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isEmpty()) {
            throw new IllegalArgumentException("An error occurred - user does not exist");
        }
        User existingUser = existingUserOptional.get();
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());

        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserByActivationCode(String activationCode) {
        User user = userRepository.getUserByActivationCode(activationCode);
        if (user == null) {
            throw new IllegalArgumentException("Kullanici bulunamadi");
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+email);
        if (user == null) {
            throw new IllegalArgumentException("Kullanici bulunamadi");
        }
        return user;
    }

    public void sendActivationEmail(String email, String activationCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Hesap Aktivasyonu");

            String content = "Merhaba,\n\nHesabinizi aktiflestirmek icin bu aktivasyon kodunu kullanin:\n\n";
            content += activationCode;

            helper.setText(content, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occured while trying to send email.");
        }
    }

    public String generateActivationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public boolean validateActivationCode(String enteredCode, User user) {
        String storedCode = user.getActivationCode();
        return enteredCode.equals(storedCode);
    }

    public void activateUser(User user) {
        user.setActive(true);
        userRepository.save(user);
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
