package com.example.ecommerceiteapp.feature.order;
import com.example.ecommerceiteapp.feature.order.dto.CreateOrderRequest;
import com.example.ecommerceiteapp.feature.order.dto.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order mapCreateOrderRequestToOrder(CreateOrderRequest createOrderRequest);

    OrderResponse mapOrderToOrderResponse(Order order);

}