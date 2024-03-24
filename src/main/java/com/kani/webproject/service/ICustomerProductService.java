package com.kani.webproject.service;

import com.kani.webproject.dto.ProductDetailDto;
import com.kani.webproject.dto.ProductDto;

import java.util.List;

public interface ICustomerProductService {
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductsByName(String productName);
    ProductDetailDto getProductDetailById(Long productId);
}
