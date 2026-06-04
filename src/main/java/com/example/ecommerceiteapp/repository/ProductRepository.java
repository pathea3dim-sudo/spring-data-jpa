package com.example.ecommerceiteapp.repository;

import com.example.ecommerceiteapp.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
