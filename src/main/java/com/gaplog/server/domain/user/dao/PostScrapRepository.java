package com.gaplog.server.domain.user.dao;

import com.gaplog.server.domain.user.domain.PostScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostScrapRepository extends JpaRepository<PostScrap, Long> {
    void deleteByUserIdAndPostId(Long userId, Long postId);
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    List<PostScrap> findAllByUserId(Long userId);
}
