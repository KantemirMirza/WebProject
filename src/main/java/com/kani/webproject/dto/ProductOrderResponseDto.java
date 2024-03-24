package com.kani.webproject.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductOrderResponseDto {
  private List<ProductDto> productDtoList;
  private Long orderAmount;
}
