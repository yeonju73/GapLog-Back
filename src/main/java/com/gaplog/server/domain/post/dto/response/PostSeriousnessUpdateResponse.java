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
public class PostSeriousnessUpdateResponse {

    private Long id;
    private int seriousnessCount;

    public static PostSeriousnessUpdateResponse of(Post post) {
        return PostSeriousnessUpdateResponse.builder()
                .id(post.getId())
                .seriousnessCount(post.getSeriousnessCount())
                .build();
    }
}
