package com.gaplog.server.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateDto {

    @NotNull
    private Long id; // 수정할 댓글 ID

    @NotBlank
    private String text; // 수정된 댓글 내용

}
