package com.kani.webproject.repository;

import com.kani.webproject.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICouponRepository extends JpaRepository<Coupon,Long> {
    boolean existsByCode(String code);
    Optional<Coupon> findByCode(String code);

}
