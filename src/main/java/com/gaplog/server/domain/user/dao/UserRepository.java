package com.gaplog.server.domain.user.dao;

import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.domain.UserRelationships;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    @Query("SELECT user FROM User user WHERE user.id = :id AND user.deleted = false")
    Optional<User> findActiveUserById(Long id);
}
