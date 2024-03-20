package com.example.metoChat.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);
}
