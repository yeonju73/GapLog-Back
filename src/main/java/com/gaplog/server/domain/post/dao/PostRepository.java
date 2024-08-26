package com.gaplog.server.domain.post.dao;

import com.gaplog.server.domain.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    //Optional<Post> setMainPage();
    List<Post> findByCategoryId(Long categoryId);
    Optional<Post> findById(Long id);
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findTop20ByOrderByCreatedAtDesc(Pageable pageable);
    List<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
}
