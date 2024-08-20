package com.gaplog.server.domain.user.application;

import com.gaplog.server.domain.user.dao.UserRelationshipsRepository;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.domain.UserRelationships;
import com.gaplog.server.domain.user.dto.UserRelationshipsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRelationshipsService {

    private final UserRelationshipsRepository userRelationshipsRepo;
    private  final UserRepository userRepository;

    @Transactional(readOnly=true)
    public List<UserRelationshipsDto> getFollowers(Long userId) {

        List<UserRelationships> followers = userRelationshipsRepo.findAllByFolloweeId(userId);

        return followers.stream()
                .map(UserRelationshipsDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public List<UserRelationshipsDto> getFollowees(Long userId) {
        List<UserRelationships> followees = userRelationshipsRepo.findAllByFolloweeId(userId);

        return followees.stream()
                .map(UserRelationshipsDto::of)
                .collect(Collectors.toList());
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
        userRelationshipsRepo.save(relationship);
    }

    private void unfollowUser(Long userId, Long targetId) {
        User follower = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid follower ID: " + userId));
        User followee = userRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid followee ID: " + targetId));

        UserRelationships relationship = new UserRelationships(follower, followee);
        userRelationshipsRepo.delete(relationship);
    }
}
