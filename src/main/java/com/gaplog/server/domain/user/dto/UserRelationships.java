package com.gaplog.server.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserRelationships {

    private Long id;
    private Long followerId;
    private Long followeeId;

    public static UserRelationships of(Long id, Long followerId, Long followeeId) {
        return new UserRelationships(id, followerId, followeeId);
    }

}
