package com.gaplog.server.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PostScrapResponse {
    private Long scarpId;

    public static PostScrapResponse of(Long id){
        return PostScrapResponse.builder().scarpId(id).build();
    }
}
