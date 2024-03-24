package com.kani.webproject.controller;

import com.kani.webproject.entity.Coupon;
import com.kani.webproject.exception.ValidationException;
import com.kani.webproject.service.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/coupon")
public class AdminCouponController {
    private final ICouponService couponService;

    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon){
        try{
            Coupon createCoupon = couponService.createCoupon(coupon);
            return ResponseEntity.ok(createCoupon);
        }catch (ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        return ResponseEntity.ok(couponService.getAllCouponList());
    }

}
