package com.gaplog.server.domain.post.application;

import com.gaplog.server.domain.post.dao.PostRepository;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.post.dto.request.PostUpdateRequestDTO;
import com.gaplog.server.domain.post.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public SelectedPostResponseDTO getSelectedPostInfo(Long postId) {
        Post getSelectedPostInfo = postRepository.findById(postId).orElseThrow(()
                ->new IllegalArgumentException("Post not found: " + postId));
        return SelectedPostResponseDTO.of(getSelectedPostInfo);
    }

    @Transactional
    public MainPostResponseDTO getMainPostInfo(Long postId) {
        Post getMainPostInfo = postRepository.findById(postId).orElseThrow(()
                ->new IllegalArgumentException("Post not found: " + postId));
        return MainPostResponseDTO.of(getMainPostInfo);
    }

    @Transactional
    public FindByCategoryPostResponseDTO getFindByCategoryPostInfo(Long postId) {
        Post getFindByCategoryPostInfo = postRepository.findById(postId).orElseThrow(()
                ->new IllegalArgumentException("Post not found: " + postId));
        return FindByCategoryPostResponseDTO.of(getFindByCategoryPostInfo);
    }

    @Transactional
    public FindByKeywordPostResponseDTO getFindByKeywordPostInfo(Long postId) {
        Post getFindByKeywordPostInfo = postRepository.findById(postId).orElseThrow(()
                ->new IllegalArgumentException("Post not found: " + postId));
        return FindByKeywordPostResponseDTO.of(getFindByKeywordPostInfo);
    }

    @Transactional
    public PostUpdateResponseDTO updatePost(Long postId, PostUpdateRequestDTO postUpdateRequestDTO) {
        Post post = postRepository.findById(postId).orElseThrow(()
            ->new IllegalArgumentException("Post not found: " + postId));

        post.updateTitle(postUpdateRequestDTO.getTitle());
        post.updateContent(postUpdateRequestDTO.getContent());

        Post updatedPost = postRepository.save(post);
        return PostUpdateResponseDTO.of(updatedPost);
    }


}
