package com.example.market_apple.Repository;

import com.example.market_apple.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Transactional
    @Query(value = "UPDATE users SET status = :status WHERE id = :id RETURNING *", nativeQuery = true)
    User updateStatusAndReturn(@Param("id") Long id, @Param("status") String status);
}