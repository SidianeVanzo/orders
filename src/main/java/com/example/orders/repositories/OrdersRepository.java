package com.example.orders.repositories;

import com.example.orders.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {

    @Query(value = "select distinct orders " +
                    "from Orders orders inner join Item item on orders.id = item.orders.id inner join Product product on item.product.id = product.id " +
                    "where orders.orderDate between :startTime and :endTime")
    List<Orders> findOrders(@Param("startTime") LocalDateTime startTime,
                            @Param("endTime") LocalDateTime endTime);
}
