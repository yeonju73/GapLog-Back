package com.gaplog.server.domain.comment.dto.response;

import com.gaplog.server.domain.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeUpdateResponse {

    private Long commentId;
    private int likes;
    private boolean isLiked;

    public static CommentLikeUpdateResponse of(Comment comment, Boolean isLiked) {
        return CommentLikeUpdateResponse.builder()
                .commentId(comment.getId())
                .likes(comment.getLikeCount())
                .isLiked(isLiked)
                .build();
    }

}
