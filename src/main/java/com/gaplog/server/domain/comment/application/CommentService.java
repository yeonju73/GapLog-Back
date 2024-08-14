package com.gaplog.server.domain.comment.application;

import com.gaplog.server.domain.comment.domain.Comment;
import com.gaplog.server.domain.comment.dto.response.CommentLikeUpdateResponse;
import com.gaplog.server.domain.comment.dto.response.CommentResponse;
import com.gaplog.server.domain.comment.dto.response.CommentUpdateResponse;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    // 댓글 작성
    public CommentResponse createComment(Long postId, Long userId, String text, Long parentId) {
        Comment comment = new Comment(1L, new Post(), new User(), text, null);
        return CommentResponse.of(comment);
    }

    // 댓글 수정
    public CommentUpdateResponse updateComment(Long commentId, String text) {
        Comment comment = new Comment(1L, new Post(), new User(), text, null);
        return CommentUpdateResponse.of(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {

    }

    // 댓글 좋아요 수 수정
    public CommentLikeUpdateResponse updateLikeCount(Long commentId, boolean like) {
        Comment comment = new Comment(1L, new Post(), new User(), "text", null);
        return CommentLikeUpdateResponse.of(comment);
    }
}
