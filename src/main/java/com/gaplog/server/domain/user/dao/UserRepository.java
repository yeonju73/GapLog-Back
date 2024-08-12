package com.gaplog.server.domain.user.dao;

import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.domain.UserRelationships;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
}
