package com.gaplog.server.domain.post.dto.response;

import com.gaplog.server.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostJinjiUpdateResponse {

    private Long id;
    private int jinjiCount;

    public static PostJinjiUpdateResponse of(Post post) {
        return PostJinjiUpdateResponse.builder()
                .id(post.getId())
                .jinjiCount(post.getJinjiCount())
                .build();
    }
}
