package com.kani.webproject.controller;

import com.kani.webproject.dto.WishListDto;
import com.kani.webproject.service.IWishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class WishListController {
    private final IWishListService wishListService;

    @PostMapping("/wishList")
    public ResponseEntity<?> addProductToWishList(@RequestBody WishListDto wishListDto){
        WishListDto dto = wishListService.addProductToWishList(wishListDto);
        if(dto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(wishListDto);
    }

    @GetMapping("/wishList/{userId}")
    public ResponseEntity<List<WishListDto>> getWishListByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(wishListService.getWishListBuUserId(userId));
    }


}
