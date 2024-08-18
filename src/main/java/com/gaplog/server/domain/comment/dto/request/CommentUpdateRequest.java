package com.gaplog.server.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CommentUpdateRequest {

    @NotNull
    private Long id; // 수정할 댓글 ID

    @NotBlank
    private String text; // 수정된 댓글 내용

}