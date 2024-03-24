package com.kani.webproject.controller;

import com.kani.webproject.dto.AnalyticResponseDto;
import com.kani.webproject.dto.OrderDto;
import com.kani.webproject.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class OrderController {
    private final IOrderService orderService;

    @GetMapping("/placeOrders")
    public ResponseEntity<List<OrderDto>> getAllPlacedOrders(){
        return ResponseEntity.ok(orderService.getAllPlacedOrders());
    }

    @GetMapping("/order/{orderId}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId, @PathVariable String status){
        OrderDto orderDto = orderService.changeOrderStatus(orderId, status);
        if(orderDto == null){
             return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/orders/analytic")
    public ResponseEntity<AnalyticResponseDto> calculateOrderAnalytics(){
        return ResponseEntity.ok(orderService.calculateAnalytics());
    }


}
