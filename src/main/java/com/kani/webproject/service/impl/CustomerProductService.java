package com.kani.webproject.service.impl;

import com.kani.webproject.dto.ProductDetailDto;
import com.kani.webproject.dto.ProductDto;
import com.kani.webproject.entity.FAQ;
import com.kani.webproject.entity.Product;
import com.kani.webproject.entity.Review;
import com.kani.webproject.repository.IFAQRepository;
import com.kani.webproject.repository.IProductRepository;
import com.kani.webproject.repository.IReviewRepository;
import com.kani.webproject.service.ICustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductService implements ICustomerProductService {
    private final IProductRepository productRepository;
    private final IFAQRepository faqRepository;
    private final IReviewRepository reviewRepository;

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
    public ProductDetailDto getProductDetailById(Long productId){
        Optional<Product> product = productRepository.findById(productId);

        if(product.isPresent()){
            List<FAQ> faqs = faqRepository.findByProductId(productId);
            List<Review> reviews = reviewRepository.findByProductId(productId);

            ProductDetailDto detailDto = new ProductDetailDto();
            detailDto.setProductDto(product.get().getDto());
            detailDto.setFaqDtoList(faqs.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));
            detailDto.setReviewDtoList(reviews.stream().map(Review::getDto).collect(Collectors.toList()));

            return detailDto;
        }
        return null;
    }
}
