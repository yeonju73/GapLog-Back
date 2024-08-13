package com.gaplog.server.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long id;
    private Long postId;
    private Long parentId;
    private Long userId;
    private String text;
    private int likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}