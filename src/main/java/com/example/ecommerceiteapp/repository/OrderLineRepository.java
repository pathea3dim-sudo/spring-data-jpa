package com.example.ecommerceiteapp.repository;

import com.example.ecommerceiteapp.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
}
