package com.example.ecommerceiteapp.feature.order;
import com.example.ecommerceiteapp.feature.order.dto.CreateOrderRequest;
import com.example.ecommerceiteapp.feature.order.dto.OrderResponse;
import com.example.ecommerceiteapp.feature.product.Product;
import com.example.ecommerceiteapp.feature.product.ProductRepository;
import com.example.ecommerceiteapp.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createNew(CreateOrderRequest createOrderRequest) {

        final Order order = orderMapper.mapCreateOrderRequestToOrder(createOrderRequest);

        List<OrderLine> orderLines = new ArrayList<>();

        // Validate order lines (LIST)
        boolean isValidOrder = createOrderRequest.orderLines().stream()
                .allMatch(orderLineDto -> {

                    Optional<Product> productOptional = productRepository
                            .findByCode(orderLineDto.code());

                    if (productOptional.isPresent()) {
                        OrderLine orderLine = new OrderLine();
                        orderLine.setProduct(productOptional.get());
                        orderLine.setQty(orderLineDto.qty());
                        orderLine.setUnitPrice(orderLineDto.unitPrice());
                        orderLine.setOrder(order);
                        orderLines.add(orderLine);
                        return true;
                    }

                    return false;
                });

        if (!isValidOrder) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid order line");
        }

        order.setOrderLines(orderLines);

        order.setCustomerId(SecurityUtils.extractUserId());


        order.setOrderedAt(LocalDateTime.now());
        order.setStatus(false);
        order.setIsDeleted(false);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.mapOrderToOrderResponse(savedOrder);
    }



}