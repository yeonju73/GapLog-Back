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
public class CommentLikeUpdateResponse {

    private Long commentId;
    private int likes;

    public static CommentLikeUpdateResponse of(Comment comment) {
        return CommentLikeUpdateResponse.builder()
                .commentId(comment.getId())
                .likes(comment.getLike_count())
                .build();
    }

}
