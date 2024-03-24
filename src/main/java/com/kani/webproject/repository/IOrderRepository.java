package com.kani.webproject.repository;

import com.kani.webproject.entity.Order;
import com.kani.webproject.enumaration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {
    Order findUserByIdAndByStatus(Long userId, OrderStatus pending);

    List<Order> findAllByOrderStatus(List<OrderStatus> orderStatus);

    List<Order> findUserByIdAndByStatusIn(Long userId, List<OrderStatus> pending);

    Optional<Order> findByTrackingId(UUID trackingId);

    List<Order> findByDateAndStatus(Date startOfMonths, Date endOfMonths, OrderStatus status);

    Long countByOrderStatus(OrderStatus placed);

}
