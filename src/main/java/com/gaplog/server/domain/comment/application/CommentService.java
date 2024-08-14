package com.gaplog.server.domain.comment.application;

import com.gaplog.server.domain.comment.dto.request.CommentRequest;
import com.gaplog.server.domain.comment.dto.response.CommentResponse;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    // 댓글 작성
    public CommentResponse createComment(CommentRequest commentRequest) {
        // Mock response
        return CommentResponse.builder()
                .id(1L)
                .postId(commentRequest.getPostId())
                .userId(commentRequest.getUserId())
                .text(commentRequest.getText())
                .likeCount(0)
                .build();
    }

    // 댓글 수정
    public CommentResponse updateComment(Long commentId, CommentRequest commentRequest) {
        // Mock response
        return CommentResponse.builder()
                .id(commentId)
                .postId(commentRequest.getPostId())
                .userId(commentRequest.getUserId())
                .text(commentRequest.getText())
                .likeCount(10) // Assume like count stays the same
                .build();
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {

    }

    // 댓글 좋아요 수 수정
    public CommentResponse updateLikeCount(Long commentId, int likeCount) {
        // Mock response
        return CommentResponse.builder()
                .id(commentId)
                .postId(1L) // Assume postId is 1 for this mock
                .userId(2L) // Assume userId is 2 for this mock
                .text("Sample comment text")
                .likeCount(likeCount)
                .build();
    }
}
