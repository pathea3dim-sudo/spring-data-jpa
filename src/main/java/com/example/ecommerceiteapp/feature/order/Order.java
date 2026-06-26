package com.example.ecommerceiteapp.feature.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    private Float discount;

    private String remark;
    private Boolean status;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

//    @OneToMany(mappedBy = "order")
//    private List<OrderLine> orderLineList;



    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderLine> orderLines;

}
