package com.kani.webproject.service.impl;

import com.kani.webproject.entity.Coupon;
import com.kani.webproject.exception.ValidationException;
import com.kani.webproject.repository.ICouponRepository;
import com.kani.webproject.service.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService implements ICouponService {
    private final ICouponRepository couponRepository;

    @Override
    public Coupon createCoupon(Coupon coupon){
        if(couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon already exists");
        }
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> getAllCouponList(){
        return couponRepository.findAll();
    }




}
