package com.gaplog.server;

import com.gaplog.server.domain.post.application.PostService;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.post.dto.request.PostLikeUpdateRequest;
import com.gaplog.server.domain.post.dto.request.PostUpdateRequest;
import com.gaplog.server.domain.post.dto.response.SelectedPostResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostTest {

    @Autowired
    private PostService postService;

    @Test
    public void testCreatePost() {
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("stringtitle1", "contentstring1");
        postService.updatePost(1L, postUpdateRequest);
        Post post2 = postService.createPost(2L, "title2", "content2", "thumbnailUrl2", 2L);
        Post post3 = postService.createPost(3L, "title3", "content3", "thumbnailUrl3", 3L);
        //postService.deletePost(3L);

        PostLikeUpdateRequest postLikeUpdateRequest = new PostLikeUpdateRequest(true);
        postService.PostLikeUpdate(1L, postLikeUpdateRequest);
        SelectedPostResponse selectedPostResponse = postService.getSelectedPostInfo(1L);

        System.out.println(selectedPostResponse.getId());
        System.out.println(selectedPostResponse.getTitle());
        System.out.println(selectedPostResponse.getContent());
        System.out.println(selectedPostResponse.getLikeCount());
        System.out.println(selectedPostResponse.getJinjiCount());
        System.out.println(selectedPostResponse.getCreatedAt());
        System.out.println(selectedPostResponse.getUpdatedAt());
    }
}
