package com.kani.webproject.service.impl;

import com.kani.webproject.dto.ProductDto;
import com.kani.webproject.dto.ProductOrderResponseDto;
import com.kani.webproject.dto.ReviewDto;
import com.kani.webproject.entity.*;
import com.kani.webproject.repository.IOrderRepository;
import com.kani.webproject.repository.IProductRepository;
import com.kani.webproject.repository.IReviewRepository;
import com.kani.webproject.repository.IUserRepository;
import com.kani.webproject.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final IOrderRepository orderRepository;
    private final IUserRepository userRepository;
    private final IProductRepository productRepository;

    private final IReviewRepository reviewRepository;

    @Override
    public ProductOrderResponseDto productOrderResponseDto(Long orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        ProductOrderResponseDto orderResponseDto = new ProductOrderResponseDto();
        if(order.isPresent()){
            orderResponseDto.setOrderAmount(order.get().getAmount());

            List<ProductDto> productList = new ArrayList<>();
            for(CartItem cartItems : order.get().getCartItems()){
                ProductDto productDto = new ProductDto();

                productDto.setId(cartItems.getProduct().getId());
                productDto.setProductName(cartItems.getProduct().getProductName());
                productDto.setPrice(cartItems.getPrice());
                productDto.setQuantity(cartItems.getQuantity());

                productDto.setByteImage(cartItems.getProduct().getImage());

                productList.add(productDto);
            }
            orderResponseDto.setProductDtoList(productList);
        }
        return orderResponseDto;
    }

    @Override
    public ReviewDto giveReviewDto(ReviewDto reviewDto) throws IOException {
        Optional<Product> product = productRepository.findById(reviewDto.getId());
        Optional<User> user = userRepository.findById(reviewDto.getId());

        if(product.isPresent() && user.isPresent()){
            Review review = new Review();
            review.setRating(review.getRating());
            review.setDescription(review.getDescription());
            review.setUser(user.get());
            review.setProduct(product.get());
            review.setImg(reviewDto.getImg().getBytes());

            return reviewRepository.save(review).getDto();
        }
        return null;
    }



}
