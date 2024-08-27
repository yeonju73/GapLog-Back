package com.gaplog.server.domain.post.dto.request;

import com.gaplog.server.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String content;
    private Long category;
    private String thumbnailUrl;
    private Long user;
}
