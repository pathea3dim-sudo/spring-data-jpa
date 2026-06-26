package com.example.ecommerceiteapp.feature.order;


import com.example.ecommerceiteapp.feature.order.dto.CreateOrderRequest;
import com.example.ecommerceiteapp.feature.order.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponse createNew(@Valid @RequestBody CreateOrderRequest createOrderRequest){

        return  orderService.createNew(createOrderRequest);
    }

    @GetMapping
    public Page<OrderResponse> findAllOrders(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size
    ){
        return orderService.findAllOrders(page, size);
    }


    @GetMapping("/id")
    public OrderResponse findOrderById(@PathVariable UUID id){
        return orderService.findOrderById(id);
    }

    @PutMapping("/{id}/soft-delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteOrder(@PathVariable UUID id){
        orderService.softDeleteOrder(id);
    }

    @DeleteMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hardDeleteOrder(@PathVariable UUID id){
        orderService.hardDeleteOrder(id);
    }

    @PutMapping("/{id}/status")
    public  OrderResponse updateOrderStatus(
            @PathVariable UUID id,
            @RequestParam Boolean status
    ){
        return  orderService.updateOrderStatus(id,status);
    }





}
