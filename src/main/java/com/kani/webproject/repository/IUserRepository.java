package com.kani.webproject.repository;

import com.kani.webproject.entity.User;
import com.kani.webproject.enumaration.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    User findByUserRole(UserRole admin);

}
