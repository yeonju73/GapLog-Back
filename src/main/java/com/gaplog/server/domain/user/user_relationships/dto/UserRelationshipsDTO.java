package com.gaplog.server.domain.user.user_relationships.dto;

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


}
