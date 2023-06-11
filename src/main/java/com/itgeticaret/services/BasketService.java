package com.itgeticaret.services;

import com.itgeticaret.models.Basket;
import com.itgeticaret.models.Product;
import com.itgeticaret.models.PurchaseOrder;
import com.itgeticaret.models.User;
import com.itgeticaret.repositories.BasketRepository;
import com.itgeticaret.repositories.OrderRepository;
import com.itgeticaret.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BasketService implements IBasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public BasketService(BasketRepository basketRepository, UserRepository userRepository) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
    }


    public void addToBasket(Product product) {
        User user = getCurrentUser();
        Basket basket = user.getBasket();

        basket.getProducts().add(product);
        basketRepository.save(basket);
    }

    @Override
    public void removeFromBasket(Product product) {
        User user = getCurrentUser();
        Basket basket = user.getBasket();

        List<Product> products = basket.getProducts();
        products.removeIf(p -> p.getName().equals(product.getName()));
        basket.setProducts(products);
        basketRepository.save(basket);
    }

    @Override
    public void checkout() {
        User user = getCurrentUser();
        Basket basket = user.getBasket();

        // Ödeme işlemleri, bildirimler vb. burada yapılır
        // ...

        basket.setProducts(new ArrayList<>());
        basketRepository.save(basket);
    }

    @Override
    @ResponseBody
    public Basket getBasket() {
        User user = getCurrentUser();
        return user.getBasket();
    }

    public void updateBasket(Basket basket) {
        basketRepository.save(basket);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByEmail(authentication.getName());
    }


    }

