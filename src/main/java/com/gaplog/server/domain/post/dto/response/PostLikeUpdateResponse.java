package com.gaplog.server.domain.post.dto.response;

import com.gaplog.server.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostLikeUpdateResponse {
    private Long id;
    private int likeCount;

    public static PostLikeUpdateResponse of(Post post) {
        return PostLikeUpdateResponse.builder()
                .id(post.getId())
                .likeCount(post.getLikeCount())
                .build();
    }
}
