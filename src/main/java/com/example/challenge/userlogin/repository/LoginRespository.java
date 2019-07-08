package com.example.challenge.userlogin.repository;

import com.example.challenge.userlogin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface LoginRespository extends JpaRepository<User, UUID> {

    User findByUserId(UUID userId);

    User findByUsernameAndPassword(String username, String password);

}
