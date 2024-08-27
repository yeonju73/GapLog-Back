package com.gaplog.server.domain.user.application;

import com.gaplog.server.domain.post.dao.PostRepository;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.dao.PostScrapRepository;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.PostScrap;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.dto.request.PostScrapRequest;
import com.gaplog.server.domain.user.dto.response.PostScrapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostScrapService {
    private final PostScrapRepository postScrapRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostScrapResponse> getScrapPosts(Long userId) {
        // 유저가 스크랩한 PostScrap 리스트를 가져옴
        List<PostScrap> postScraps = postScrapRepository.findAllByUserId(userId);

        // 스크랩한 게시물의 포스트 정보 반환
        return postScraps.stream()
                .map(postScrap -> {
                    Post post = postRepository.findById(postScrap.getPost().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postScrap.getPost().getId()));
                    return PostScrapResponse.of(postScrap.getId(), post);
                })
                .collect(Collectors.toList());
    }

}

