package com.example.ecommerceiteapp.feature.order;


import com.example.ecommerceiteapp.feature.order.dto.CreateOrderRequest;
import com.example.ecommerceiteapp.feature.order.dto.OrderResponse;
import com.example.ecommerceiteapp.feature.product.Product;
import com.example.ecommerceiteapp.feature.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements  OrderService{


    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final  OrderMapper orderMapper;

    @Override
    public OrderResponse findOrderById(UUID id) {
        Order order=orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted with ID: "+id));

        return orderMapper.mapOrderToOrderResponse(order);
    }

    @Override
    public Page<OrderResponse> findAllOrders(int page, int size) {
        if(page<0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page index must be greater than or equal to 0");
        }
        if(size<=0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page size must be greater than 0");
        }

        Pageable pageable= PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "orderedAt"));

        Page<Order> orderPage=orderRepository.findAllByIsDeletedFalse(pageable);
        return orderPage.map(orderMapper::mapOrderToOrderResponse);
    }

    @Override
    public void softDeleteOrder(UUID id) {
        Order order=orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted with ID: "+ id));

        orderRepository.softDeleteById(id);
        log.info("Order soft delete wit ID :{}"+id);

    }

    @Override
    public void hardDeleteOrder(UUID id) {

        if(!orderRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with ID :"+id);
        }
        orderRepository.deleteById(id);
        log.info("Order hard delete wit ID :{}"+id);


    }

    @Override
    public OrderResponse updateOrderStatus(UUID id, Boolean status) {
        Order order=orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been delete with ID"));

        orderRepository.updateStatusById(id, status);

        order.setStatus(status);
        return orderMapper.mapOrderToOrderResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse createNew(CreateOrderRequest createOrderRequest) {


        List<OrderLine> orderLines =new ArrayList<>();
        //validate order line
     boolean isValidOrder=   createOrderRequest.orderLines().stream()
                .allMatch(orderLineDto -> {
//                    Optional<Product> product=productRepository.findByCode(orderLineDto.code())
//                            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
//                                    "Product code has not been found"));
                    Optional<Product> productOptional=productRepository.findByCode(orderLineDto.code());


                    if(productOptional.isPresent()){
                        OrderLine orderLine=new OrderLine();
                        orderLine.setProduct(productOptional.get());
                        orderLine.setQty(orderLineDto.qty());
                        orderLine.setUnitPrice(orderLineDto.unitPrice());
                        orderLines.add(orderLine);
                        return true;
                    }
                    return false;
                });

     if(!isValidOrder){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Order line");
     }
     Order order=new Order();
     order.setAddress(createOrderRequest.address());
     order.setDiscount(createOrderRequest.discount());
     order.setRemark(createOrderRequest.remark());
     //security retated
     order.setCustomerId("ISTAD");

     order.setIsDeleted(false);
     order.setOrderedAt(LocalDateTime.now());
     order.setStatus(false);

     order=orderRepository.save(order);


//        return null;
        return orderMapper.mapOrderToOrderResponse(order);
    }



}
