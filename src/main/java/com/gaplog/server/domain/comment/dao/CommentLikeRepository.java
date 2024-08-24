package com.gaplog.server.domain.comment.dao;

import com.gaplog.server.domain.comment.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    // 특정 댓글에 대한 좋아요 조회
    List<CommentLike> findByCommentId(Long commentId);

    // 특정 사용자에 의한 좋아요 조회
    List<CommentLike> findByUserId(Long userId);

    // 특정 사용자와 댓글에 의한 좋아요 조회
    CommentLike findByUserIdAndCommentId(Long userId, Long commentId);

    // 특정 댓글에 대한 좋아요 수 카운트
    long countByCommentId(Long commentId);

    // 좋아요 삭제 메소드
    void deleteByUserIdAndCommentId(Long userId, Long commentId);

    // 특정 사용자와 댓글에 의한 좋아요 존재 여부 조회
    boolean existsByUserIdAndCommentId(Long userId, Long commentId);

}
