package com.kani.webproject.service.impl;

import com.kani.webproject.dto.WishListDto;
import com.kani.webproject.entity.Product;
import com.kani.webproject.entity.User;
import com.kani.webproject.entity.WishList;
import com.kani.webproject.repository.IProductRepository;
import com.kani.webproject.repository.IUserRepository;
import com.kani.webproject.repository.IWishListRepository;
import com.kani.webproject.service.IWishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService implements IWishListService {

    private final IUserRepository userRepository;
    private final IProductRepository productRepository;

    private final IWishListRepository wishListRepository;

    @Override
    public WishListDto addProductToWishList(WishListDto wishListDto){
        Optional<Product> product = productRepository.findById(wishListDto.getId());
        Optional<User> user = userRepository.findById(wishListDto.getId());

        if(product.isPresent() && user.isPresent()) {
            WishList wishList = new WishList();
            wishList.setProduct(product.get());
            wishList.setUser(user.get());
            wishListRepository.save(wishList).responseWishListDto();
        }
        return null;
    }

    @Override
    public List<WishListDto> getWishListBuUserId(Long userId){
        return wishListRepository.findAllByUserId(userId).stream()
                .map(WishList::responseWishListDto).collect(Collectors.toList());
    }


}
