package com.example.ecommerceiteapp.feature.order;


import com.example.ecommerceiteapp.feature.order.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OrderMapper {
//    OrderResponse

    @Mapping(source = "orderedAt" , target = "ordersAt")
    @Mapping(source = "isDeleted", target = "isDelete")
    OrderResponse mapOrderToOrderResponse(Order orders);

    List<OrderResponse> mapOrdersToOrderResponses(List<Order> orderPage);

    default Page<OrderResponse> mapPageToOrderResponsePage(Page<Order> orderPage){
        return  orderPage.map(this::mapOrderToOrderResponse);
    }


}
