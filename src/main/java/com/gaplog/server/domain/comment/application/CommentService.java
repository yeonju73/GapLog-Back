package com.gaplog.server.domain.comment.application;

import com.gaplog.server.domain.comment.dao.CommentRepository;
import com.gaplog.server.domain.comment.domain.Comment;
import com.gaplog.server.domain.comment.dto.response.CommentLikeUpdateResponse;
import com.gaplog.server.domain.comment.dto.response.CommentResponse;
import com.gaplog.server.domain.comment.dto.response.CommentUpdateResponse;
import com.gaplog.server.domain.post.dao.PostRepository;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Transactional
    public CommentResponse createComment(Long postId, Long userId, String text, Long parentId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        User user = userRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Comment newComment = Comment.of(post, user, text, parentId);

        commentRepository.save(newComment);
        return CommentResponse.of(newComment);
    }

    @Transactional
    public CommentUpdateResponse updateComment(Long commentId, String text) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        comment.setText(text);
        commentRepository.save(comment);
        return CommentUpdateResponse.of(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        List<Comment> childComments = commentRepository.findByParentId(commentId);

        // Soft Delete
        // isDeleted == true 라면 댓글이 사용자에게 노출되지 않음
        // 하지만 repository 상에서 삭제 되진 않음

        // case 1: 부모 댓글 : 자식 댓글이 1개 이상 있는  -> isDeletedParent = ture
        // case 2: 부모 댓글 : 자식 댓글이 없는 (대댓글이 없는 댓글) -> isDeleted = true
        // case 3: 자식 댓글  -> isDeleted = true & 부모 댓글의 유일한 댓글인지 검사
        // -> 부모 댓글의 childComments 의 크키가 1이라면, 부모 댓글의 isDeleted = true & 둘 다 save

        // case 1: 삭제하려는 댓글에게 자식 댓글이 있을 때
        if(!childComments.isEmpty()) {
            comment.setDeletedParent(true);
            commentRepository.save(comment);
        }
        else { // 삭제하려는 댓글에게 자식 댓글이 없을 때

            Comment parentComment;

            // 삭제하려는 댓글에게 부모 댓글이 있을 때
            if (comment.getParentId() != null) {
                parentComment = commentRepository.findById(comment.getParentId())
                        .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + comment.getParentId()));

                // case 3: 부모 댓글의 자식이 1개(지금 삭제하는 댓글)이며, 부모가 이미 삭제상태인 댓글
                if((childComments.size() == 1) && parentComment.getIsDeletedParent()){
                    parentComment.setDeleted(true);
                    commentRepository.save(parentComment);
                }
            }
            //case 2 & case 3의 자식 댓글 setDeleted
            comment.setDeleted(true);
            commentRepository.save(comment);
        }
    }

    @Transactional
    public CommentLikeUpdateResponse updateLikeCount(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        comment.toggleLike(userId); // 좋아요 추가, 취소
        commentRepository.save(comment);

        return CommentLikeUpdateResponse.of(comment);
    }
}