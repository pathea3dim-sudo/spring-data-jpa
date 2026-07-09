package com.example.ecommerceiteapp.feature.order;

import com.example.ecommerceiteapp.feature.order.dto.CreateOrderRequest;
import com.example.ecommerceiteapp.feature.order.dto.OrderResponse;

public interface OrderService {
    OrderResponse createNew(CreateOrderRequest createOrderRequest);

}
