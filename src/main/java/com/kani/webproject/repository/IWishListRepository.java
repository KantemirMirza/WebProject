package com.kani.webproject.repository;

import com.kani.webproject.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWishListRepository extends JpaRepository<WishList,Long> {

    List<WishList> findAllByUserId(Long userId);
}
