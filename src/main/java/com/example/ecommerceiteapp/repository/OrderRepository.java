package com.example.ecommerceiteapp.repository;

import com.example.ecommerceiteapp.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
