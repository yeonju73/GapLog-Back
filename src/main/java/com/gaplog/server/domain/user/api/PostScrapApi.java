package com.gaplog.server.domain.user.api;

import com.gaplog.server.domain.user.application.PostScrapService;
import com.gaplog.server.domain.user.dto.request.PostScrapRequest;
import com.gaplog.server.domain.user.dto.response.PostScrapResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/{user_id}/scraps")
@RequiredArgsConstructor
@Tag(name = "post-scrap API", description = "게시물 스크랩 관리 API")
public class PostScrapApi {
    private final PostScrapService postScrapService;

    @Operation(summary = "게시글 스크랩", description = "유저가 특정 게시물을 스크랩합니다.")
    @PostMapping
    public ResponseEntity<PostScrapResponse> scrapPost(
            @PathVariable("user_id") Long userId,
            @RequestBody PostScrapRequest requestDTO) {
        PostScrapResponse responseDTO = postScrapService.scrapPost(userId, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "스크랩 삭제", description = "유저가 특정 게시글의 스크랩을 삭제합니다.")
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Void> removeScrap(
            @PathVariable("user_id") Long userId,
            @PathVariable("post_id") Long postId) {
        postScrapService.removeScrap(userId, postId);
        return ResponseEntity.noContent().build();
    }

}
