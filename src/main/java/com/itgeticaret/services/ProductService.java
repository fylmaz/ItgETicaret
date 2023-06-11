package com.itgeticaret.services;

import com.itgeticaret.enums.Gender;
import com.itgeticaret.models.Product;
import com.itgeticaret.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("An error occurred - product does not exist");
        }
        return productOptional.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(long id,Product product) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isEmpty()) {
            throw new IllegalArgumentException("An error occurred - product does not exist");
        }
        Product existingProduct = existingProductOptional.get();
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        //System.out.println("Existing Product: " + existingProduct.toString());
        //System.out.println("Updated Product: " + product.toString());
        productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public List<Product> findByGender(Gender gender) {
        if (gender == null || gender == Gender.ALL) {
            return getAllProducts();
        }
        return productRepository.findByGender(gender);
    }
}
