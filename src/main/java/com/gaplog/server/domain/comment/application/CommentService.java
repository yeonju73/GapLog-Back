package com.gaplog.server.domain.comment.application;

import com.gaplog.server.domain.comment.dto.request.CommentRequestDto;
import com.gaplog.server.domain.comment.dto.response.CommentResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    // 댓글 작성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        // Mock response
        return CommentResponseDto.builder()
                .id(1L)
                .postId(commentRequestDto.getPostId())
                .userId(commentRequestDto.getUserId())
                .text(commentRequestDto.getText())
                .likeCount(0)
                .build();
    }

    // 댓글 수정
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        // Mock response
        return CommentResponseDto.builder()
                .id(commentId)
                .postId(commentRequestDto.getPostId())
                .userId(commentRequestDto.getUserId())
                .text(commentRequestDto.getText())
                .likeCount(10) // Assume like count stays the same
                .build();
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {

    }

    // 댓글 좋아요 수 수정
    public CommentResponseDto updateLikeCount(Long commentId, int likeCount) {
        // Mock response
        return CommentResponseDto.builder()
                .id(commentId)
                .postId(1L) // Assume postId is 1 for this mock
                .userId(2L) // Assume userId is 2 for this mock
                .text("Sample comment text")
                .likeCount(likeCount)
                .build();
    }
}
