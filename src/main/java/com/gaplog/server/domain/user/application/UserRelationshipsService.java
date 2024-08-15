package com.gaplog.server.domain.user.application;

import com.gaplog.server.domain.user.dao.UserRelationshipsRepository;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.dto.UserRelationships;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRelationshipsService {

    private final UserRelationshipsRepository userRelationshipsRepository;
    private  final UserRepository userRepository;

    public List<UserRelationships> getFollowers(Long userId) {

        List<UserRelationships> followers = new ArrayList<>();
        followers.add(UserRelationships.of(1L, 2L, userId));
        return followers;
    }

    public List<UserRelationships> getFollowees(Long userId) {
        List<UserRelationships> followees = new ArrayList<>();
        followees.add(UserRelationships.of(1L, 2L, userId));
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

        com.gaplog.server.domain.user.domain.UserRelationships relationship = new com.gaplog.server.domain.user.domain.UserRelationships(follower, followee);
        userRelationshipsRepository.save(relationship);
    }

    private void unfollowUser(Long userId, Long targetId) {
        User follower = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid follower ID: " + userId));
        User followee = userRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid followee ID: " + targetId));

        com.gaplog.server.domain.user.domain.UserRelationships relationship = new com.gaplog.server.domain.user.domain.UserRelationships(follower, followee);
        userRelationshipsRepository.delete(relationship);
    }
}
