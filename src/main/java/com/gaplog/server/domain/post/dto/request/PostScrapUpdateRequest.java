package com.gaplog.server.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostScrapUpdateRequest {
    //private Long postId;
    private boolean scrap;
}
