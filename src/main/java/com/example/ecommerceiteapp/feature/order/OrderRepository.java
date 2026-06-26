//package com.example.ecommerceiteapp.feature.order;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//import java.util.UUID;
//
//public interface OrderRepository extends JpaRepository<Order, UUID> {
//
//
//
//    Page<Order> findAllByIsDeletedFalse(Pageable pageable);
//
//    Optional<Order> findByIdAndIsDeletedFalse(UUID id);
//    @Modifying
//    @Transactional
//    @Query("UPDATE Order o SET o.isDeleted =true WHERE o.id=:id")
//    void softDeletedById(@Param("id") UUID id);
//    @Query("UPDATE Order o SET o.status = :status WHERE o.id=:id")
//    void updateStatusById(@Param("id") UUID id, @Param("status") Boolean status);
//}


package com.example.ecommerceiteapp.feature.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Page<Order> findAllByIsDeletedFalse(Pageable pageable);
    Optional<Order> findByIdAndIsDeletedFalse(UUID id);
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.isDeleted = true WHERE o.id = :id")
    void softDeleteById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    void updateStatusById(@Param("id") UUID id, @Param("status") Boolean status);
}
