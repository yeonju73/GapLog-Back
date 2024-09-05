package com.gaplog.server.domain.comment.dto.response;

import com.gaplog.server.domain.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt() != null
                        ? LocalDateTime.parse(comment.getCreatedAt().toString())
                        : LocalDateTime.now())
                .updatedAt(comment.getUpdatedAt() != null
                        ? LocalDateTime.parse(comment.getUpdatedAt().toString())
                        : LocalDateTime.now())
                .build();
    }

}