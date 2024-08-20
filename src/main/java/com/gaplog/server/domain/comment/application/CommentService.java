package com.gaplog.server.domain.comment.application;

import com.gaplog.server.domain.caterory.domain.Category;
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
import java.util.Optional;

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

        //임시적으로 부모 댓글이 삭제되면 자식 댓글도 삭제되는 로직으로 구현
        List<Comment> childComments = commentRepository.findByParentId(commentId);
        if (!childComments.isEmpty()) {
            commentRepository.deleteAll(childComments);
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public CommentLikeUpdateResponse updateLikeCount(Long commentId, boolean like) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        // like가 true면 like을 새롭게 누른 것, false면 눌렀던 like를 지운 것으로 생각했습니다.
        if (like){
            comment.setLikeCount(comment.getLikeCount() + 1);
        }else{
            if (comment.getLikeCount() > 0){
                comment.setLikeCount(comment.getLikeCount() - 1);
            }
        }

        commentRepository.save(comment);

        return CommentLikeUpdateResponse.of(comment);
    }
}