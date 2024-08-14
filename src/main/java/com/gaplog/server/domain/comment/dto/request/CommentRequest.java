package com.gaplog.server.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CommentRequest {

    @NotNull
    private Long postId;

    @NotNull
    private Long userId;

    @NotBlank
    private String text;

    private Long parentId;
}