package com.gaplog.server.domain.comment.application;

import com.gaplog.server.domain.comment.dao.CommentLikeRepository;
import com.gaplog.server.domain.comment.dao.CommentRepository;
import com.gaplog.server.domain.comment.domain.Comment;
import com.gaplog.server.domain.comment.dto.response.CommentResponse;
import com.gaplog.server.domain.comment.dto.response.CommentUpdateResponse;
import com.gaplog.server.domain.post.dao.PostRepository;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentServiceTest {

//    @Autowired
//    private CommentService commentService;
//    @Autowired
//    private CommentRepository commentRepository;
//    @Autowired
//    private PostRepository postRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private CommentLikeRepository commentLikeRepository;
//
//    private User user;
//    private Post post;
//    @BeforeEach
//    void setUp(){
//        user = new User(3L, "testUser");
//        userRepository.save(user);
//
//        post = Post.of("testPost", "0", null, "tsetUrl", user);
//        postRepository.save(post);
//    }
//
//    @Test
//    @DisplayName("댓글 생성 테스트")
//    void createCommentTest(){
//        // Given
//
//        // When
//        CommentResponse response = commentService.createComment(post.getId(), user.getId(), "testCommment1", null);
//        Long commentId = response.getCommentId();
//        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("comment not found"));
//
//        // Then
//        assertEquals(comment.getText(), "testCommment1");
//    }
//
//    @Test
//    @DisplayName("댓글 수정 테스트")
//    void updateCommentTest(){
//        // Given
//        CommentResponse response = commentService.createComment(post.getId(), user.getId(), "testCommment1", null);
//        Long commentId = response.getCommentId();
//
//        // When
//        CommentUpdateResponse updateResponse = commentService.updateComment(commentId, "updateTest");
//        Comment comment = commentRepository.findById(updateResponse.getCommentId()).orElseThrow(() -> new IllegalArgumentException("comment not found"));
//
//        // Then
//        assertEquals(comment.getText(), "updateTest");
//    }
//
//    @Test
//    @DisplayName("댓글 삭제 테스트 - 자식 댓글이 있는 경우")
//    void deleteParentCommentWithChildComments() {
//        // Given
//        CommentResponse parentResponse = commentService.createComment(post.getId(), user.getId(), "parentComment", null);
//        Long parentId = parentResponse.getCommentId();
//        commentService.createComment(post.getId(), user.getId(), "childComment1", parentId);
//        commentService.createComment(post.getId(), user.getId(), "childComment2", parentId);
//
//        // When
//        Comment parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
//        assertFalse(parentComment.getIsDeletedParent());
//        assertFalse(parentComment.getIsDeleted());
//
//        commentService.deleteComment(parentId);
//
//        // Then
//        parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
//        assertTrue(parentComment.getIsDeletedParent());
//        assertFalse(parentComment.getIsDeleted());
//    }
//
//    @Test
//    @DisplayName("댓글 삭제 테스트 - 자식 댓글이 없는 경우")
//    void deleteCommentWithoutChildComments() {
//        // Given
//        CommentResponse response = commentService.createComment(post.getId(), user.getId(), "testComment", null);
//        Long commentId = response.getCommentId();
//
//        // When
//        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
//        assertFalse(comment.getIsDeleted());
//
//        commentService.deleteComment(commentId);
//
//        // Then
//        comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
//        assertTrue(comment.getIsDeleted());
//    }
//
//    @Test
//    @DisplayName("댓글 삭제 테스트 - 부모댓글도 삭제되는 경우")
//    void deleteChildComment() {
//        // Given
//        CommentResponse parentResponse = commentService.createComment(post.getId(), user.getId(), "parentComment", null);
//        Long parentId = parentResponse.getCommentId();
//        CommentResponse childResponse1 = commentService.createComment(post.getId(), user.getId(), "childComment", parentId);
//        Long childId1 = childResponse1.getCommentId();
//        CommentResponse childResponse2 = commentService.createComment(post.getId(), user.getId(), "childComment", parentId);
//        Long childId2 = childResponse2.getCommentId();
//
//        // When
//        //부모 댓글 삭제
//        commentService.deleteComment(parentId);
//        Comment parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
//        assertTrue(parentComment.getIsDeletedParent());
//        assertFalse(parentComment.getIsDeleted());
//
//        //첫번째 자식 댓글 삭제
//        commentService.deleteComment(childId1);
//        Comment childComment1 = commentRepository.findById(childId1).orElseThrow(() -> new IllegalArgumentException("Child comment not found"));
//        assertTrue(childComment1.getIsDeleted());
//
//        parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
//        assertFalse(parentComment.getIsDeleted());
//
//        //마지막 자식 댓글 삭제
//        commentService.deleteComment(childId2);
//        Comment childComment2 = commentRepository.findById(childId1).orElseThrow(() -> new IllegalArgumentException("Child comment not found"));
//        assertTrue(childComment2.getIsDeleted());
//
//        // Then
//        //부모댓글도 완전히 삭제
//        parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
//        assertTrue(parentComment.getIsDeleted());
//    }
    /*
    @Test
    @DisplayName("동시에 4명이 comment의 좋아요를 증가시킬때 ")
    public void likeUpdateSameTime() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(200);

        CountDownLatch countDownLatch = new CountDownLatch(4);

        User user1 = userRepository.findById(10L).orElseThrow(() -> new IllegalArgumentException("user not found"));
        User user2 = userRepository.findById(11L).orElseThrow(() -> new IllegalArgumentException("user not found"));
        User user3 = userRepository.findById(12L).orElseThrow(() -> new IllegalArgumentException("user not found"));
        User user4 = userRepository.findById(13L).orElseThrow(() -> new IllegalArgumentException("user not found"));

        CommentResponse commentResponse = commentService.createComment(post.getId(), user1.getId(), "testComment", null);
        Long commentId = commentResponse.getCommentId();

        executorService.execute(()->{
            try{
                commentService.updateLikeCount(user1.getId(), commentId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally{
                countDownLatch.countDown();
            }
        });

        executorService.execute(()->{
            try{
                commentService.updateLikeCount(user2.getId(), commentId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally{
                countDownLatch.countDown();
            }
        });

        executorService.execute(()->{
            try{
                commentService.updateLikeCount(user3.getId(), commentId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally{
                countDownLatch.countDown();
            }
        });

        executorService.execute(()->{
            try{
                commentService.updateLikeCount(user4.getId(), commentId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally{
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();

        Comment comment = commentRepository.findById(commentId).get();
        assertEquals(comment.getLikeCount(), 4);
    }
    */
}
