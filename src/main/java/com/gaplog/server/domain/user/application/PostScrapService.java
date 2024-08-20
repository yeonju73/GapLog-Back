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

    @Transactional(readOnly = true)
    public List<PostScrapResponse> getScrapPosts(Long userId) {
        // Next: post aggregate 완성되면 postId 반환되도록 수정 필요

        List<PostScrap> postScraps = postScrapRepository.findAllByUserId(userId);
        return postScraps.stream()
                .map(postScrap -> PostScrapResponse.of(postScrap.getId()))
                .collect(Collectors.toList());
    }
}

