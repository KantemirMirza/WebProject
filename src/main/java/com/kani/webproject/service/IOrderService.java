package com.kani.webproject.service;

import com.kani.webproject.dto.AnalyticResponseDto;
import com.kani.webproject.dto.OrderDto;

import java.util.List;

public interface IOrderService {
    List<OrderDto> getAllPlacedOrders();

    OrderDto changeOrderStatus(Long orderId, String status);

    AnalyticResponseDto calculateAnalytics();
}
