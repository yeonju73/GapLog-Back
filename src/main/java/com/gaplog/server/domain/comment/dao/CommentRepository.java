package com.gaplog.server.domain.comment.dao;

import com.gaplog.server.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);

    //특정 부모 댓글에 대한 모든 자식 댓글을 조회
    List<Comment> findByParentId(Long parentId);

    List<Comment> findByUserId(Long userId);

    //특정 post에 대해 특정 user가 작성한 모든 댓글을 조회
    List<Comment> findByPostIdAndUserId(Long postId, Long userId);

    //특정 post 에서 최상위(부모 댓글이 없는) comment를 조회
    List<Comment> findByPostIdAndParentIdIsNull(Long postId);

    //특정 comment의 좋아요 수 조회
    int findLikeCountById(Long Id);

    //특정 게시글의 댓글 수 조회
    long countByPostId(Long postId);

    //특정 post의 comment 삭제
    void deleteByPostId(Long postId);

    // 특정 사용자의 댓글 삭제
    void deleteByUserId(Long userId);

}
