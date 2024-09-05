package com.gaplog.server.domain.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaplog.server.domain.comment.application.CommentService;
import com.gaplog.server.domain.comment.dao.CommentLikeRepository;
import com.gaplog.server.domain.comment.dao.CommentRepository;
import com.gaplog.server.domain.comment.domain.Comment;
import com.gaplog.server.domain.comment.domain.CommentLike;
import com.gaplog.server.domain.comment.dto.response.CommentLikeUpdateResponse;
import com.gaplog.server.domain.comment.dto.response.CommentResponse;
import com.gaplog.server.domain.comment.dto.response.CommentUpdateResponse;
import com.gaplog.server.domain.post.dao.PostRepository;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentServiceTest {

    private CommentService commentService;
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentLikeRepository commentLikeRepository;

    @BeforeEach
    void setUp() {
        commentRepository = mock(CommentRepository.class);
        postRepository = mock(PostRepository.class);
        userRepository = mock(UserRepository.class);
        commentLikeRepository = mock(CommentLikeRepository.class);
        commentService = new CommentService(commentRepository, postRepository, userRepository, commentLikeRepository);
    }

    private User user;
    private Post post;


    @Test
    @DisplayName("댓글 생성 테스트")
    void createCommentTest() throws Exception {

        Long postId = 1L;
        Long userId = 1L;
        String text = "Test comment";
        Long parentId = null;

        Post post = new Post(); // Post 객체 생성
        User user = new User(); // User 객체 생성

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CommentResponse response = commentService.createComment(postId, userId, text, parentId);

        assertNotNull(response);

        assertEquals(text, response.getText());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateCommentTest(){
        Long commentId = 1L;
        String newText = "Updated text";

        Comment comment = new Comment();
        comment.setText("Old text");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentUpdateResponse response = commentService.updateComment(commentId, newText);

        assertNotNull(response);
        assertEquals(newText, response.getText());
    }

    @Test
    void deleteComment_ShouldSetDeletedTrue() {
        Long commentId = 1L;

        Comment comment = new Comment();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.findByParentId(commentId)).thenReturn(List.of());

        commentService.deleteComment(commentId);

        assertTrue(comment.getIsDeleted());
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    @DisplayName("댓글 삭제 테스트 - 자식 댓글이 있는 경우")
    void deleteParentCommentWithChildComments() {
        // Given
        Long parentId = 1L;
        Comment parentComment = new Comment();

        when(commentRepository.findById(parentId)).thenReturn(Optional.of(parentComment));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 자식 댓글 생성
        Comment childComment1 = new Comment();
        Comment childComment2 = new Comment();

        when(commentRepository.findByParentId(parentId)).thenReturn(List.of(childComment1, childComment2));

        // When
        assertFalse(parentComment.getIsDeletedParent());
        assertFalse(parentComment.getIsDeleted());

        commentService.deleteComment(parentId);

        // Then
        parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
        assertTrue(parentComment.getIsDeletedParent());
        assertFalse(parentComment.getIsDeleted());
    }

    @Test
    void updateLikeCount_ShouldReturnCommentLikeUpdateResponse() {
        Long userId = 1L;
        Long commentId = 1L;

        User user = new User();
        Comment comment = new Comment();
        comment.setLikeCount(0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentLikeRepository.existsByUserIdAndCommentId(userId, commentId)).thenReturn(false);
        when(commentLikeRepository.save(any(CommentLike.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentLikeUpdateResponse response = commentService.updateLikeCount(userId, commentId);

        assertNotNull(response);
        assertEquals(1, response.getLikes());
        assertTrue(response.isLiked());
    }

    @Test
    void updateLikeCount_ShouldDecreaseLikeCount() {
        Long userId = 1L;
        Long commentId = 1L;

        User user = new User();
        Comment comment = new Comment();
        comment.setLikeCount(1);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentLikeRepository.existsByUserIdAndCommentId(userId, commentId)).thenReturn(true);
        doNothing().when(commentLikeRepository).deleteByUserIdAndCommentId(userId, commentId);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentLikeUpdateResponse response = commentService.updateLikeCount(userId, commentId);

        assertNotNull(response);
        assertEquals(0, response.getLikes());
        assertFalse(response.isLiked());
    }


    /*
    @Test
    @DisplayName("댓글 삭제 테스트 - 자식 댓글이 있는 경우")
    void deleteParentCommentWithChildComments() {
        // Given
        CommentResponse parentResponse = commentService.createComment(post.getId(), user.getId(), "parentComment", null);
        Long parentId = parentResponse.getCommentId();
        commentService.createComment(post.getId(), user.getId(), "childComment1", parentId);
        commentService.createComment(post.getId(), user.getId(), "childComment2", parentId);

        // When
        Comment parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
        assertFalse(parentComment.getIsDeletedParent());
        assertFalse(parentComment.getIsDeleted());

        commentService.deleteComment(parentId);

        // Then
        parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
        assertTrue(parentComment.getIsDeletedParent());
        assertFalse(parentComment.getIsDeleted());
    }

    @Test
    @DisplayName("댓글 삭제 테스트 - 자식 댓글이 없는 경우")
    void deleteCommentWithoutChildComments() {
        // Given
        CommentResponse response = commentService.createComment(post.getId(), user.getId(), "testComment", null);
        Long commentId = response.getCommentId();

        // When
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        assertFalse(comment.getIsDeleted());

        commentService.deleteComment(commentId);

        // Then
        comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        assertTrue(comment.getIsDeleted());
    }

    @Test
    @DisplayName("댓글 삭제 테스트 - 부모댓글도 삭제되는 경우")
    void deleteChildComment() {
        // Given
        CommentResponse parentResponse = commentService.createComment(post.getId(), user.getId(), "parentComment", null);
        Long parentId = parentResponse.getCommentId();
        CommentResponse childResponse1 = commentService.createComment(post.getId(), user.getId(), "childComment", parentId);
        Long childId1 = childResponse1.getCommentId();
        CommentResponse childResponse2 = commentService.createComment(post.getId(), user.getId(), "childComment", parentId);
        Long childId2 = childResponse2.getCommentId();

        // When
        //부모 댓글 삭제
        commentService.deleteComment(parentId);
        Comment parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
        assertTrue(parentComment.getIsDeletedParent());
        assertFalse(parentComment.getIsDeleted());

        //첫번째 자식 댓글 삭제
        commentService.deleteComment(childId1);
        Comment childComment1 = commentRepository.findById(childId1).orElseThrow(() -> new IllegalArgumentException("Child comment not found"));
        assertTrue(childComment1.getIsDeleted());

        parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
        assertFalse(parentComment.getIsDeleted());

        //마지막 자식 댓글 삭제
        commentService.deleteComment(childId2);
        Comment childComment2 = commentRepository.findById(childId1).orElseThrow(() -> new IllegalArgumentException("Child comment not found"));
        assertTrue(childComment2.getIsDeleted());

        // Then
        //부모댓글도 완전히 삭제
        parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
        assertTrue(parentComment.getIsDeleted());
    }

    @Test
    @DisplayName("한 유저가 좋아요 눌렀다 지우기")
    void likeUpdateTest() throws InterruptedException {
        // Given
        CommentResponse response = commentService.createComment(post.getId(), user.getId(), "testComment", null);
        Long commentId = response.getCommentId();

        // When
        commentService.updateLikeCount(user.getId(), commentId);

        // Then
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        assertEquals(comment.getLikeCount(), 1);

        // When
        commentService.updateLikeCount(user.getId(), commentId);

        // Then
        comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        assertEquals(comment.getLikeCount(), 0);
    }

    @Test
    @DisplayName("여러 유저가 좋아요 누르기")
    void likeUpdateTest2() throws InterruptedException {
        // Given
        User user2 = new User(2L, "testUser2");
        userRepository.save(user2);

        CommentResponse response = commentService.createComment(post.getId(), user.getId(), "testComment", null);
        Long commentId = response.getCommentId();

        // When
        commentService.updateLikeCount(user.getId(), commentId);
        commentService.updateLikeCount(user2.getId(), commentId);

        // Then
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        assertEquals(comment.getLikeCount(), 2);
    }

    @Test
    @DisplayName("CommentLike로 좋아요 누른 유저 찾기")
    void findCommentLikeTest3() throws InterruptedException {
        // Given
        CommentResponse response = commentService.createComment(post.getId(), user.getId(), "testComment", null);
        Long commentId = response.getCommentId();

        // When
        commentService.updateLikeCount(user.getId(), commentId);
        List<CommentLike> commentLikes = commentLikeRepository.findByCommentId(commentId);

        // Then
        assertThat(commentLikes).hasSize(1);
        assertEquals(commentLikes.getFirst().getUser().getId(), user.getId());
    }

     */


    /*
    @Test
    @DisplayName("동시에 4명이 comment의 좋아요를 증가시킬때 ")
    public void likeUpdateSameTime() throws InterruptedException {
        User user2 = new User(2L, "testUser2");
        userRepository.save(user2);
        User user3 = new User(3L, "testUser3");
        userRepository.save(user3);
        User user4 = new User(4L, "testUser4");
        userRepository.save(user4);



        CommentResponse commentResponse = commentService.createComment(post.getId(), user.getId(), "testComment", null);
        Long commentId = commentResponse.getCommentId();

        System.out.println("user 생성 완");
        Thread.sleep(100); // 댓글 생성 후 100ms 대기

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CountDownLatch countDownLatch = new CountDownLatch(4);

        executorService.execute(()->{
            try{
                commentService.updateLikeCount(user.getId(), commentId);
            } finally{
                countDownLatch.countDown();
            }
        });

        executorService.execute(()->{
            try{
                commentService.updateLikeCount(user2.getId(), commentId);
            } finally{
                countDownLatch.countDown();
            }
        });

        executorService.execute(()->{
            try{
                commentService.updateLikeCount(user3.getId(), commentId);
            } finally{
                countDownLatch.countDown();
            }
        });

        executorService.execute(()->{
            try{
                commentService.updateLikeCount(user4.getId(), commentId);
            } finally{
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();

        Comment comment = commentRepository.findById(commentId).get();
        assertEquals(4, comment.getLikeCount());
    }

     */

    private static String asJsonString(final Object obj) {
        try {
            String result = new ObjectMapper().writeValueAsString(obj);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
