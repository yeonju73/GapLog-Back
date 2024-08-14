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
public class CommentUpdateResponse {

    private Long commentId;
    private LocalDateTime updatedAt;

    public static CommentUpdateResponse of(Comment comment) {
        return CommentUpdateResponse.builder()
                .commentId(comment.getId())
                .updatedAt(LocalDateTime.parse(comment.getUpdatedAt().toString()))
                .build();
    }

}
