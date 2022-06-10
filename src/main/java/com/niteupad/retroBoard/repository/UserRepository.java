package com.niteupad.retroBoard.repository;

import com.niteupad.retroBoard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
