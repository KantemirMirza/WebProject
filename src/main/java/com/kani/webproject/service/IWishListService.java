package com.kani.webproject.service;

import com.kani.webproject.dto.WishListDto;

import java.util.List;

public interface IWishListService {
    WishListDto addProductToWishList(WishListDto wishListDto);
    List<WishListDto> getWishListBuUserId(Long userId);
}
