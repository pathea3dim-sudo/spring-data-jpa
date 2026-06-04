package com.example.ecommerceiteapp.domain;

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

    private Integer quantity;
    private BigDecimal unitPrice;

    @ManyToOne
    private Order order;

    @ManyToOne
    private  Product product;





}
