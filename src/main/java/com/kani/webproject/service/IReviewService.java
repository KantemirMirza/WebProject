package com.kani.webproject.service;

import com.kani.webproject.dto.ProductOrderResponseDto;
import com.kani.webproject.dto.ReviewDto;

import java.io.IOException;

public interface IReviewService {
    ProductOrderResponseDto productOrderResponseDto(Long orderId);

    ReviewDto giveReviewDto(ReviewDto reviewDto) throws IOException;

}
