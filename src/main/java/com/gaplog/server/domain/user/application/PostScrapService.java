package com.gaplog.server.domain.user.application;

import com.gaplog.server.domain.post.dao.PostRepository;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.dao.PostScrapRepository;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.PostScrap;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.dto.request.PostScrapRequestDTO;
import com.gaplog.server.domain.user.dto.response.PostScrapResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostScrapService {
    private final PostScrapRepository postScrapRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @Transactional
    public PostScrapResponseDTO scrapPost(Long userId, PostScrapRequestDTO postScrapRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        Post post = postRepository.findById(postScrapRequestDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postScrapRequestDTO.getPostId()));

        PostScrap postScrap = new PostScrap(user, post);
        postScrapRepository.save(postScrap);

        return PostScrapResponseDTO.of(postScrap.getId());
    }

    @Transactional
    public void removeScrap(Long userId, Long postId) {
        if (!postScrapRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new IllegalArgumentException("No Scrap");
        }
        postScrapRepository.deleteByUserIdAndPostId(userId, postId);
    }


}
