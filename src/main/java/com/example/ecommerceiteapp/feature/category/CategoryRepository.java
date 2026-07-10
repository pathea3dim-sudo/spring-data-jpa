package com.example.ecommerceiteapp.feature.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);
    Page<Category> findByIsDeleteFalse(Pageable pageable);

    Optional<Category> findByIdAndIsDeleteFalse(Integer id);

    List<Category> findByParentCategoryIdAndIsDeleteFalse(Integer parentCategoryId);

    boolean existsByNameAndIsDeleteFalse(String name);

    List<Category> findByParentCategoryId(Integer parentCategoryId);
}