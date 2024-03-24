package com.kani.webproject.entity;

import com.kani.webproject.dto.WishListDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public WishListDto responseWishListDto(){
        WishListDto dto = new WishListDto();

        dto.setId(id);
        dto.setProductId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setReturnedImg(product.getImage());
        dto.setProductDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setUserId(user.getId());
        return dto;
    }
}
