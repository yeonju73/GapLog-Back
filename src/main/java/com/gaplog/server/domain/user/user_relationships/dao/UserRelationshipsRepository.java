package com.gaplog.server.domain.user.user_relationships.dao;

import com.gaplog.server.domain.user.user_relationships.domain.UserRelationships;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRelationshipsRepository extends JpaRepository <UserRelationships, Long> {
}
