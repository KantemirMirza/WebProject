package com.kani.webproject.service.impl;

import com.kani.webproject.dto.AnalyticResponseDto;
import com.kani.webproject.dto.OrderDto;
import com.kani.webproject.entity.Order;
import com.kani.webproject.enumaration.OrderStatus;
import com.kani.webproject.repository.IOrderRepository;
import com.kani.webproject.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;

    @Override
    public List<OrderDto> getAllPlacedOrders(){
        List<Order> orders = orderRepository.findAllByOrderStatus(List.of(
                OrderStatus.PLACED,
                OrderStatus.SHIPPED,
                OrderStatus.DELIVERED));
        return orders.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto changeOrderStatus(Long orderId, String status){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();

            if(Objects.equals(status, "Shipping")){
                order.setOrderStatus(OrderStatus.SHIPPED);
            }else if(Objects.equals(status, "Delivered")){
                order.setOrderStatus(OrderStatus.DELIVERED);
            }
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }

    @Override
    public AnalyticResponseDto calculateAnalytics(){
        LocalDate currentDate = LocalDate.now();
        LocalDate previousDate = currentDate.minusMonths(1);

        Long currentMonthsOrder = getTotalOrdersForMonths(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthsOrder = getTotalOrdersForMonths(previousDate.getMonthValue(), previousDate.getYear());

        Long currentMonthsEarnings = getTotalEarningForMonths(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthsEarnings = getTotalOrdersForMonths(previousDate.getMonthValue(), previousDate.getYear());

        Long placed = orderRepository.countByOrderStatus(OrderStatus.PLACED);
        Long shipped = orderRepository.countByOrderStatus(OrderStatus.SHIPPED);
        Long delivered = orderRepository.countByOrderStatus(OrderStatus.DELIVERED);

        return new AnalyticResponseDto(placed, shipped, delivered,
                currentMonthsOrder, previousMonthsOrder, currentMonthsEarnings, previousMonthsEarnings);
    }

    private Long getTotalEarningForMonths(int monthValue, int year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthValue - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Date startOfMonths = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonths = calendar.getTime();

        List<Order> orders = orderRepository.findByDateAndStatus(startOfMonths, endOfMonths, OrderStatus.DELIVERED);

        Long sum = 0L;
        for(Order order : orders){
            sum += order.getAmount();
        }
        return sum;
    }

    private Long getTotalOrdersForMonths(int monthValue, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthValue - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Date startOfMonths = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonths = calendar.getTime();

        List<Order> orders = orderRepository.findByDateAndStatus(startOfMonths, endOfMonths, OrderStatus.DELIVERED);
        return (long) orders.size();
    }


}
