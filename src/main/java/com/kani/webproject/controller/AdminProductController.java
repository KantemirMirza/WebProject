package com.kani.webproject.controller;

import com.kani.webproject.dto.FAQDto;
import com.kani.webproject.dto.ProductDto;
import com.kani.webproject.service.IFAQService;
import com.kani.webproject.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {
    private final IProductService productService;
    private final IFAQService faqService;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> createProduct(@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto product = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productList = productService.getAllProducts();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/searchProduct/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name){
        List<ProductDto> productList = productService.getAllProductsByName(name);
        return ResponseEntity.ok(productList);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        Boolean deleted = productService.deleteProduct(productId);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/faq/{productId}")
    public ResponseEntity<FAQDto> createFAQ(@PathVariable Long productId, FAQDto faqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(faqService.createFaq(productId, faqDto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        ProductDto product = productService.getProductById(productId);
        if(product != null){
            return ResponseEntity.ok(product);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @ModelAttribute ProductDto productDto) throws IOException {
        ProductDto productUpdate = productService.updateProduct(productId, productDto);
        if(productUpdate != null){
            return ResponseEntity.ok(productUpdate);
        }else{
            return ResponseEntity.notFound().build();
        }
    }



}
