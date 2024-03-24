package com.kani.webproject.controller;

import com.kani.webproject.dto.ProductDetailDto;
import com.kani.webproject.dto.ProductDto;
import com.kani.webproject.service.ICustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerProductController {
    private final ICustomerProductService customerService;

    @GetMapping("/allProducts")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productList = customerService.getAllProducts();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/searchProduct/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name){
        List<ProductDto> productList = customerService.getAllProductsByName(name);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetailById(@PathVariable Long productId){

        ProductDetailDto productDetailDto = customerService.getProductDetailById(productId);
        if(productDetailDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDetailDto);
    }



}
