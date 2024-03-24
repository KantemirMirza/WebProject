package com.kani.webproject.repository;

import com.kani.webproject.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFAQRepository extends JpaRepository<FAQ,Long> {
    List<FAQ> findByProductId(Long productId);

}
