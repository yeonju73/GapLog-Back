package com.gaplog.server.domain.post.application;

import com.gaplog.server.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
