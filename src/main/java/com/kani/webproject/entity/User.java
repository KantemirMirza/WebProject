package com.kani.webproject.entity;

import com.kani.webproject.enumaration.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String userName;

    private UserRole userRole;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte [] image;
}
