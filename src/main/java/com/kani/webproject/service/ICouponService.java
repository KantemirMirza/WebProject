package com.kani.webproject.service;

import com.kani.webproject.entity.Coupon;

import java.util.List;

public interface ICouponService {
    Coupon createCoupon(Coupon coupon);
    List<Coupon> getAllCouponList();
}
