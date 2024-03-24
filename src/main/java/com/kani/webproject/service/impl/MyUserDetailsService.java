package com.kani.webproject.service.impl;

import com.kani.webproject.dto.SignupRequest;
import com.kani.webproject.dto.UserDto;
import com.kani.webproject.entity.Order;
import com.kani.webproject.entity.User;
import com.kani.webproject.enumaration.OrderStatus;
import com.kani.webproject.enumaration.UserRole;
import com.kani.webproject.repository.IOrderRepository;
import com.kani.webproject.repository.IUserRepository;
import com.kani.webproject.service.IMyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements IMyUserDetailsService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IOrderRepository orderRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Not found");
        }
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getUserName(),
                optionalUser.get().getPassword(), new ArrayList<>());
    }
    @Override
    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public UserDto createUser(SignupRequest register) {
        User user = new User();
        user.setEmail(register.getEmail());
        user.setUserName(register.getUserName());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setUser(createdUser);
        order.setOrderStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        return userDto;
    }
    @Override
    public Boolean hasUserWithEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void createAdminAccount() {
        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if(adminAccount == null){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setUserName("admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }

}
