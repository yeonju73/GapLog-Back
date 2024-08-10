package com.gaplog.server.domain.user.user_relationships.dto;

import com.gaplog.server.domain.user.user_relationships.domain.UserRelationships;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserRelationshipsDTO {

    private Long id;
    private Long followerId;
    private Long followeeId;

    public static UserRelationshipsDTO of(Long id, Long followerId, Long followeeId) {
        return new UserRelationshipsDTO(id, followerId, followeeId);
    }

    public static UserRelationshipsDTO of(UserRelationships entity) {
        return new UserRelationshipsDTO(
                entity.getId(),
                entity.getFollower().getId(),
                entity.getFollowee().getId()
        );
    }

}
