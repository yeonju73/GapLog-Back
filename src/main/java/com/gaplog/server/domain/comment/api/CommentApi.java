package com.gaplog.server.domain.comment.api;

import com.gaplog.server.domain.comment.application.CommentService;
import com.gaplog.server.domain.comment.dto.request.CommentRequestDto;
import com.gaplog.server.domain.comment.dto.response.CommentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "Comment", description = "Comment API")
public class CommentApi {

    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "댓글 작성", description = "댓글을 작성합니다.")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto createdComment = commentService.createComment(commentRequestDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{comment_id}")
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long comment_id,
                                                            @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto updatedComment = commentService.updateComment(comment_id, commentRequestDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{comment_id}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteComment(@PathVariable Long comment_id) {
        commentService.deleteComment(comment_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{comment_id}/likes")
    @Operation(summary = "댓글 좋아요 수 수정", description = "댓글의 좋아요 수를 수정합니다.")
    public ResponseEntity<CommentResponseDto> updateLikeCount(@PathVariable Long comment_id,
                                                              @RequestBody int likeCount) {
        CommentResponseDto updatedComment = commentService.updateLikeCount(comment_id, likeCount);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }
}
