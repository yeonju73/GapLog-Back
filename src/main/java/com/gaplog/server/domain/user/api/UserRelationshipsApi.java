package com.gaplog.server.domain.user.api;

import com.gaplog.server.domain.user.application.UserRelationshipsService;
import com.gaplog.server.domain.user.dto.request.UpdateFollowRequestDTO;
import com.gaplog.server.domain.user.dto.UserRelationshipsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "user-relationships API", description = "유저 간 관계 관리 API")
public class UserRelationshipsApi {

    private final UserRelationshipsService userRelationshipsService;

    @Operation(summary = "유저의 팔로워 목록 조회", description = "특정 유저를 팔로우 하고 있는 목록을 조회합니다.")
    @GetMapping("/{user_id}/followers")
    public ResponseEntity<List<UserRelationshipsDTO>> getFollowers(@PathVariable("user_id") Long userId) {
        List<UserRelationshipsDTO> followers = userRelationshipsService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    @Operation(summary = "유저의 팔로우 목록 조회", description = "특정 유저가 팔로우 하고 있는 목록을 조회합니다.")
    @GetMapping("/{user_id}/followees")
    public ResponseEntity<List<UserRelationshipsDTO>> getFollowees(@PathVariable("user_id") Long userId) {
        List<UserRelationshipsDTO> followees = userRelationshipsService.getFollowees(userId);
        return ResponseEntity.ok(followees);
    }

    @Operation(summary = "유저의 팔로우 목록 수정", description = "유저가 팔로우 하고 있는 목록을 수정합니다.")
    @PutMapping("/{user_id}/follow")
    public ResponseEntity<String> updateFollow(@PathVariable("user_id") Long userId,
     @RequestBody UpdateFollowRequestDTO updateFollowRequestDTO){

        String action = updateFollowRequestDTO.getAction();
        Long targetId = updateFollowRequestDTO.getTargetId();

        try {
            String result = userRelationshipsService.updateFollow(userId, targetId, action);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
