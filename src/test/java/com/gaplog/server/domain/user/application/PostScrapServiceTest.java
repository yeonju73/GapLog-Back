package com.gaplog.server.domain.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.gaplog.server.domain.post.dao.PostRepository;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.dao.PostScrapRepository;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.PostScrap;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.dto.response.PostScrapResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PostScrapServiceTest {

    @Autowired
    private PostScrapService postScrapService;

    @Autowired
    private PostScrapRepository postScrapRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Post post1;
    private Post post2;


    @BeforeEach
    void setUp() {
        // 이미 존재하는 유저를 가져옴
        user = userRepository.findById(65L).orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 새로운 포스트 생성
        post1 = Post.builder()
                .title("Test Post")
                .content("This is a test post")
                .thumbnailUrl("test_thumbnail.jpg")
                .user(user)
                .category(null) // 카테고리 필요시 추가
                .build();
        postRepository.save(post1);

        post2 = Post.builder()
                .title("Test Post")
                .content("This is a test post")
                .thumbnailUrl("test_thumbnail.jpg")
                .user(user)
                .category(null) // 카테고리 필요시 추가
                .build();
        postRepository.save(post2);

        // 유저가 새 포스트를 스크랩
        postScrapRepository.save(new PostScrap(user, post1));
        postScrapRepository.save(new PostScrap(user, post2));
    }

    @Test
    @DisplayName("유저가 스크랩한 게시물 리스트를 반환")
    void testGetScrapPosts() {
        List<PostScrapResponse> responses = postScrapService.getScrapPosts(user.getId());

        assertEquals(2, responses.size());
        assertEquals(post1.getId(), responses.get(0).getPostId());
        assertEquals(post2.getId(), responses.get(1).getPostId());
    }

}
