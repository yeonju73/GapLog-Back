package com.gaplog.server.domain.comment.api;

import com.gaplog.server.domain.comment.application.CommentService;
import com.gaplog.server.domain.comment.dto.request.CommentLikeUpdateRequest;
import com.gaplog.server.domain.comment.dto.request.CommentRequest;
import com.gaplog.server.domain.comment.dto.request.CommentUpdateRequest;
import com.gaplog.server.domain.comment.dto.response.CommentLikeUpdateResponse;
import com.gaplog.server.domain.comment.dto.response.CommentResponse;
import com.gaplog.server.domain.comment.dto.response.CommentUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request) {
        try{
            CommentResponse response = commentService.createComment(request.getPostId(), request.getUserId(), request.getText(), request.getParentId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{comment_id}")
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    public ResponseEntity<CommentUpdateResponse> updateComment(@PathVariable ("comment_id") Long commentId, @RequestBody CommentUpdateRequest request) {
        try{
            CommentUpdateResponse response = commentService.updateComment(commentId, request.getText());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{comment_id}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteComment(@PathVariable("comment_id") Long comment_id) {
        try{
            commentService.deleteComment(comment_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{comment_id}/likes")
    @Operation(summary = "댓글 좋아요 수 수정", description = "댓글의 좋아요 수를 수정합니다.")
    public ResponseEntity<CommentLikeUpdateResponse> updateLikeCount(@PathVariable ("comment_id") Long commentId, @RequestBody CommentLikeUpdateRequest request) {
        try{//Authorization 헤더에서 userid 토큰을 추출 하는 방법도
            //근데 저능해서 아직 모르겠음
            CommentLikeUpdateResponse response = commentService.updateLikeCount(commentId, request.getUserId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
