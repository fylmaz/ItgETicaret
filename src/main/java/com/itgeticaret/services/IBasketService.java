package com.itgeticaret.services;

import com.itgeticaret.models.Basket;
import com.itgeticaret.models.Product;

public interface IBasketService {

    //User class has a relation with Basket class as a composition.
    // Since a Basket object has no meaning without a User,
    // it shouldn't exist without it. So when a user is deleted, basket objects will be deleted as well.
    // Basket class object must be constructed when a User registered the system.
    // That's why I didn't put createBasket and deleteBasket method in any class.
    void addToBasket(Product product);

    void removeFromBasket(Product productDto);

    void checkout();

    Basket getBasket();
}

