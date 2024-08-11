package com.gaplog.server.domain.user.api;

import com.gaplog.server.domain.user.application.UserRelationshipsService;
import com.gaplog.server.domain.user.dto.UserRelationshipsDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "user-relationships", description = "user-relationships API")
public class UserRelationshipsApi {

    private final UserRelationshipsService userRelationshipsService;

    // 유저의 팔로워 목록 조회
    @GetMapping("/{user_id}/followers")
    public ResponseEntity<List<UserRelationshipsDTO>> getFollowers(@PathVariable("user_id") Long userId) {
        List<UserRelationshipsDTO> followers = userRelationshipsService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    // 유저의 팔로우 목록 조회
    @GetMapping("/{user_id}/followees")
    public ResponseEntity<List<UserRelationshipsDTO>> getFollowees(@PathVariable("user_id") Long userId) {
        List<UserRelationshipsDTO> followees = userRelationshipsService.getFollowees(userId);
        return ResponseEntity.ok(followees);
    }

    // 팔로우 관계 삭제




}
