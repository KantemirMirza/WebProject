package com.kani.webproject.service.impl;

import com.kani.webproject.dto.AddProductInCartDto;
import com.kani.webproject.dto.CartItemDto;
import com.kani.webproject.dto.OrderDto;
import com.kani.webproject.dto.PlaceOrderDto;
import com.kani.webproject.entity.*;
import com.kani.webproject.enumaration.OrderStatus;
import com.kani.webproject.exception.ValidationException;
import com.kani.webproject.repository.*;
import com.kani.webproject.service.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final IOrderRepository orderRepository;
    private final IUserRepository userRepository;
    private final IProductRepository productRepository;
    private final ICartItemRepository cartItemRepository;
    private final ICouponRepository couponRepository;

    @Override
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findUserByIdAndByStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
        Optional<CartItem> cartItemOptional = cartItemRepository.findByProductIdAndByOrderIdAndByUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );
        if(cartItemOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else{
           Optional<Product> products = productRepository.findById(addProductInCartDto.getProductId());
           Optional<User> users = userRepository.findById(addProductInCartDto.getUserId());

           if(products.isPresent() && users.isPresent()) {
               CartItem cart = new CartItem();
               cart.setProduct(products.get());
               cart.setPrice(products.get().getPrice());
               cart.setQuantity(1L);
               cart.setUser(users.get());
               cart.setOrder(activeOrder);

               CartItem update = cartItemRepository.save(cart);
               activeOrder.setTotalAmount(activeOrder.getTotalAmount());
               activeOrder.setAmount((long) (activeOrder.getAmount() + cart.getPrice()));
               activeOrder.getCartItems().add(cart);

               orderRepository.save(activeOrder);

               return ResponseEntity.status(HttpStatus.CREATED).body(cart);
           }else{
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product not found");
           }
        }
    }

    @Override
    public OrderDto getCartByUserId(Long userId){

        Order activeOrder = orderRepository.findUserByIdAndByStatus(userId, OrderStatus.PENDING);
        List<CartItemDto> cartItemDtoList = activeOrder.getCartItems().stream().map(CartItem::cartItemDto).collect(Collectors.toList());

        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItemDtoList(cartItemDtoList);
        if(activeOrder.getCoupon() != null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }

    @Override
    public OrderDto applyCoupon(Long userId, String code){
        Order activeOrder = orderRepository.findUserByIdAndByStatus(userId, OrderStatus.PENDING);
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(()-> new ValidationException("Coupon not found"));
        if(couponIsExpired(coupon)){
            throw new ValidationException("Coupon has Expired");
        }
        double discountAmount = ((coupon.getDiscount() / 100.D ) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;

        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long)discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon) {
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();

        return expirationDate != null && currentDate.after(expirationDate);
    }

    @Override
    public OrderDto IncreaseProductQuantity(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findUserByIdAndByStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItem> optionalCartItem = cartItemRepository
                .findByProductIdAndByOrderIdAndByUserId(
                        addProductInCartDto.getProductId(),
                        activeOrder.getId(),
                        addProductInCartDto.getUserId());
        if(optionalProduct.isPresent() && optionalCartItem.isPresent()){
            CartItem cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount((long) (activeOrder.getAmount() +  product.getPrice()));
            activeOrder.setTotalAmount((long) (activeOrder.getTotalAmount() + product.getPrice()));

            cartItem.setQuantity(cartItem.getQuantity() + 1);
            if(activeOrder != null){
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.D ) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long)discountAmount);
            }
            cartItemRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    @Override
    public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findUserByIdAndByStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItem> optionalCartItem = cartItemRepository
                .findByProductIdAndByOrderIdAndByUserId(
                        addProductInCartDto.getProductId(),
                        activeOrder.getId(),
                        addProductInCartDto.getUserId());
        if(optionalProduct.isPresent() && optionalCartItem.isPresent()){
            CartItem cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount((long) (activeOrder.getAmount() -  product.getPrice()));
            activeOrder.setTotalAmount((long) (activeOrder.getTotalAmount() - product.getPrice()));

            cartItem.setQuantity(cartItem.getQuantity() - 1);
            if(activeOrder != null){
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.D ) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long)discountAmount);
            }
            cartItemRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    @Override
    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order activeOrder = orderRepository.findUserByIdAndByStatus(placeOrderDto.getUserId(), OrderStatus.PENDING);
        Optional<User> user = userRepository.findById(placeOrderDto.getUserId());
        if(user.isPresent()){
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.PLACED);
            activeOrder.setTrackingId(UUID.randomUUID());
            orderRepository.save(activeOrder);

            Order order = new Order();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setUser(user.get());
            order.setOrderStatus(OrderStatus.PENDING);
            orderRepository.save(order);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    @Override
    public List<OrderDto> getMyPlacedOrders(Long userId){
        return orderRepository.findUserByIdAndByStatusIn(userId, List.of(OrderStatus.PLACED,
                OrderStatus.SHIPPED, OrderStatus.DELIVERED)).stream().map(Order::getOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto searchOrderByTrackingId(UUID trackingId){
        Optional<Order> order = orderRepository.findByTrackingId(trackingId);
        if(order.isPresent()){
            return order.get().getOrderDto();
        }
        return null;
    }
}
