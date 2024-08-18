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
public class PostScrapUpdateResponse {
    private Long id;
    private int scrapCount;

    public static PostScrapUpdateResponse of(Post post) {
        return PostScrapUpdateResponse.builder()
                .id(post.getId())
                .scrapCount(post.getScrapCount())
                .build();
    }
}
