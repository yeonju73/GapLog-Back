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
        //삭제하려는 댓글에게 자식 댓글이 있을 때 IsDeleted 상태만 변경
        //삭제하려는 댓글의 id를 parentId로 갖고 있는 댓글 list
        if(!childComments.isEmpty()) {
            comment.changeIsDeleted(true);
            commentRepository.save(comment);
        }
        else { //자식 댓글이 없을 때 댓글 삭제

            // 현재 삭제하려는 댓글에게 부모댓글이 있고,
            // 부모 댓글이 자식이 1개(지금 삭제하는 댓글)이고,
            // 부모가 이미 삭제상태인 댓글이라면 (isDeleted == true 라면)
            Comment parentComment;
            if (comment.getParentId() != null) {
                parentComment = commentRepository.findById(comment.getParentId())
                        .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + comment.getParentId()));

                if((childComments.size() == 1) && parentComment.getIsDeleted()){
                    commentRepository.delete(parentComment);
                }
            }
            commentRepository.delete(comment);
        }
    }

    @Transactional
    public CommentLikeUpdateResponse updateLikeCount(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        comment.toggleLike(userId); // 좋아요 추가,취소

        commentRepository.save(comment);

        return CommentLikeUpdateResponse.of(comment);
    }
}