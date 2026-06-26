package com.example.ecommerceiteapp.feature.order;

import com.example.ecommerceiteapp.feature.order.dto.CreateOrderRequest;
import com.example.ecommerceiteapp.feature.order.dto.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface OrderService {



    OrderResponse findOrderById(UUID id);

    Page<OrderResponse> findAllOrders(int page, int size);

    void softDeleteOrder(UUID id);
    void hardDeleteOrder(UUID id);

    OrderResponse updateOrderStatus(UUID id, Boolean status);

    OrderResponse createNew(CreateOrderRequest createOrderRequest);
}
