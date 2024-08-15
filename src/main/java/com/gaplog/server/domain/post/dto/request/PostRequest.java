package com.gaplog.server.domain.post.dto.request;

import com.gaplog.server.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostRequest {
    private String title;
    private String content;
    private String category;
    private String thumbnailUrl;
    private User user;
}
