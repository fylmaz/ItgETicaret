package com.itgeticaret.repositories;

import com.itgeticaret.enums.Gender;
import com.itgeticaret.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.gender = :gender")
    List<Product> findByGender(Gender gender);
}
