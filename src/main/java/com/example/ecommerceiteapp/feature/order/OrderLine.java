package com.example.ecommerceiteapp.feature.order;

import com.example.ecommerceiteapp.feature.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_lines")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Boolean isDelete;
    @Column(nullable = false)

    private Integer qty;
    @Column(nullable = false)

    private BigDecimal unitPrice;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;





}
