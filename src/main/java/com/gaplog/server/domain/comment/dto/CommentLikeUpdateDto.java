package com.gaplog.server.domain.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeUpdateDto {

    @NotNull
    private Long id; // 댓글 ID

    @NotNull
    private int likeCount; // 새로운 좋아요 수
}