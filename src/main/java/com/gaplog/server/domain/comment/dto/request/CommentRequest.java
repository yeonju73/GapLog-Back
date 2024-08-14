package com.gaplog.server.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CommentRequest {

    @NotNull
    private Long postId;

    @NotBlank
    private String text;

    private Long parentId;

    @NotNull
    private Long userId;

}