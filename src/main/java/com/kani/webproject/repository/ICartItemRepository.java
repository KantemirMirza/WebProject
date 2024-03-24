package com.kani.webproject.repository;

import com.kani.webproject.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByProductIdAndByOrderIdAndByUserId(Long productId, Long id, Long userId);
}
