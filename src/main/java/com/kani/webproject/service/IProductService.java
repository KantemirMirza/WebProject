package com.kani.webproject.service;

import com.kani.webproject.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface IProductService {

    ProductDto createProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductsByName(String productName);

    Boolean deleteProduct(Long id);
    ProductDto getProductById(Long productId);

    ProductDto updateProduct(Long productId, ProductDto productDto)throws IOException;
}
