package com.gaplog.server.domain.user.dao;

import com.gaplog.server.domain.user.domain.UserRelationships;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRelationshipsRepository extends JpaRepository <UserRelationships, Long> {
    List<UserRelationships> findAllByFolloweeId(Long followeeId);
    boolean existsByFollowerIdAndFolloweeId(Long userId, Long targetId);
}
