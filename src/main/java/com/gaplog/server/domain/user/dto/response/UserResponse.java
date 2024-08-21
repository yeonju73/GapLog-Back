package com.gaplog.server.domain.user.dto.response;

import com.gaplog.server.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserResponse {

    private Long userId;
    private String nickName;
    private String introduce;
    private String profileImg;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponse of(User user){
        return UserResponse.builder()
                .userId(user.getId())
                .nickName(user.getNickName())
                .introduce(user.getIntroduce())
                .profileImg(user.getProfileImg())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
