package com.gaplog.server.domain.user.application;

import com.gaplog.server.domain.user.dao.UserRelationshipsRepository;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.domain.UserRelationships;
import com.gaplog.server.domain.user.dto.UserRelationshipsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRelationshipsService {

    private final UserRelationshipsRepository userRelationshipsRepository;
    private  final UserRepository userRepository;

    public List<UserRelationshipsDTO> getFollowers(Long userId) {

        List<UserRelationshipsDTO> followers = new ArrayList<>();
        followers.add(UserRelationshipsDTO.of(1L, 2L, userId));
        return followers;
    }

    public List<UserRelationshipsDTO> getFollowees(Long userId) {
        List<UserRelationshipsDTO> followees = new ArrayList<>();
        followees.add(UserRelationshipsDTO.of(1L, 2L, userId));
        return followees;
    }

    @Transactional
    public String updateFollow(Long userId, Long targetId, String action) {
        if ("follow".equals(action)) {
            followUser(userId, targetId);
            return "follower: " + userId + " followee: " + targetId;
        } else if ("unfollow".equals(action)) {
            unfollowUser(userId, targetId);
            return "follower: " + userId + " followee: " + targetId;
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
    }

    private void followUser(Long userId, Long targetId) {
        User follower = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid follower ID: " + userId));
        User followee = userRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid followee ID: " + targetId));

        UserRelationships relationship = new UserRelationships(follower, followee);
        userRelationshipsRepository.save(relationship);
    }

    private void unfollowUser(Long userId, Long targetId) {
        User follower = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid follower ID: " + userId));
        User followee = userRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid followee ID: " + targetId));

        UserRelationships relationship = new UserRelationships(follower, followee);
        userRelationshipsRepository.delete(relationship);
    }
}
