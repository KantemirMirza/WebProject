package com.kani.webproject.entity;

import com.kani.webproject.dto.OrderDto;
import com.kani.webproject.enumaration.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID trackingId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItem> cartItems;

    public OrderDto getOrderDto(){
        OrderDto order = new OrderDto();

        order.setId(id);
        order.setOrderDescription(orderDescription);
        order.setAddress(address);
        order.setTrackingId(trackingId);
        order.setAmount(amount);
        order.setDate(date);
        order.setOrderStatus(orderStatus);
        order.setUserName(user.getUserName());

        if(coupon != null){
            order.setCouponName(coupon.getName());
        }
        return order;
    }


}
