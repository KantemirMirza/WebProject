package com.kani.webproject.service.impl;

import com.kani.webproject.dto.ProductDto;
import com.kani.webproject.entity.Category;
import com.kani.webproject.entity.Product;
import com.kani.webproject.repository.ICategoryRepository;
import com.kani.webproject.repository.IProductRepository;
import com.kani.webproject.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    @Override
    public ProductDto createProduct(ProductDto productDto) throws IOException {
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage().getBytes());

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();
        product.setCategory(category);
        return productRepository.save(product).getDto();
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductsByName(String productName) {
        List<Product> products = productRepository.findAllProductsByName(productName);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public Boolean deleteProduct(Long id){
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ProductDto getProductById(Long productId){
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            return product.get().getDto();
        }else {
            return null;
        }
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> product = productRepository.findById(productId);
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        if(product.isPresent() && category.isPresent()){
            Product productCreated = product.get();
            productCreated.setProductName(productDto.getProductName());
            productCreated.setPrice(productDto.getPrice());
            productCreated.setDescription(productDto.getDescription());
            productCreated.setCategory(category.get());

            if(productDto.getImage() != null){
                productCreated.setImage(productDto.getImage().getBytes());
            }
            return productRepository.save(productCreated).getDto();
        }else{
            return null;
        }
    }


}
