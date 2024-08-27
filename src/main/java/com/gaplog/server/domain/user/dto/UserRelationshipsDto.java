package com.gaplog.server.domain.user.dto;

import com.gaplog.server.domain.user.domain.UserRelationships;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserRelationshipsDto {

    private Long id;
    private Long followerId;
    private Long followeeId;

//    public static UserRelationshipsDto of(Long id, Long followerId, Long followeeId) {
//        return new UserRelationshipsDto(id, followerId, followeeId);
//    }

    public static UserRelationshipsDto of(UserRelationships entity) {
        return new UserRelationshipsDto(
                entity.getId(),
                entity.getFollower().getId(),
                entity.getFollowee().getId()
        );
    }
}
