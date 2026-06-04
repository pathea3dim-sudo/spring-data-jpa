package com.example.ecommerceiteapp.repository;

import com.example.ecommerceiteapp.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
