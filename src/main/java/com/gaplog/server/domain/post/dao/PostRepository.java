package com.gaplog.server.domain.post.dao;

import com.gaplog.server.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    //Optional<Post> setMainPage();
    Optional<Post> findByCategory(String Category);
    Optional<Post> findById(Long id);
    Optional<Post> findByKeyword(String Keyword);
}
