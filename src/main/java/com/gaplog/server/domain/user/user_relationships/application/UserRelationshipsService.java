package com.gaplog.server.domain.user.user_relationships.application;

import com.gaplog.server.domain.user.user_relationships.dto.UserRelationshipsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRelationshipsService {
    public List<UserRelationshipsDTO> getFollowers(Long userId) {

        // Mock Data
        List<UserRelationshipsDTO> followers = new ArrayList<>();
        followers.add(UserRelationshipsDTO.of(1L, 2L, userId));
        return followers;
    }

    public List<UserRelationshipsDTO> getFollowees(Long userId) {
        List<UserRelationshipsDTO> followees = new ArrayList<>();
        followees.add(UserRelationshipsDTO.of(1L, 2L, userId));
        return followees;
    }
}
