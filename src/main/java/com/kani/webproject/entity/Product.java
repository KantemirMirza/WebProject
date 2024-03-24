package com.kani.webproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kani.webproject.dto.ProductDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private Double price;

    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte [] image;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    public ProductDto getDto(){
        ProductDto productDto = new ProductDto();
        productDto.setProductName(productName);
        productDto.setPrice(price);
        productDto.setDescription(description);
        productDto.setByteImage(image);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(category.getCategoryName());
        return productDto;
    }

}
