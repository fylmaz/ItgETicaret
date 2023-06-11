package com.itgeticaret.services;

import com.itgeticaret.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(long id);
    List<Product> getAllProducts();
    void createProduct(Product product);
    void updateProduct(long id,Product product);
    void deleteProduct(long id);
    void deleteProduct(Product product);
}

