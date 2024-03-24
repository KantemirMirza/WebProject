package com.kani.webproject.controller;

import com.kani.webproject.dto.ProductOrderResponseDto;
import com.kani.webproject.dto.ReviewDto;
import com.kani.webproject.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class ReviewController {
    private final IReviewService reviewService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ProductOrderResponseDto> productOrderResponseDto(@PathVariable Long orderId){
        return ResponseEntity.ok(reviewService.productOrderResponseDto(orderId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> createReview(@ModelAttribute ReviewDto reviewDto) throws IOException {
        ReviewDto review = reviewService.giveReviewDto(reviewDto);

        if(review == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went Wrong!!!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewDto);
    }



}
