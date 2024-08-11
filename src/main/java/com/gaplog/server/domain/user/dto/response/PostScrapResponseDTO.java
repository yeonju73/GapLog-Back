package com.gaplog.server.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostScrapResponseDTO {
    private Long scarpId;

    public static PostScrapResponseDTO of(Long id){
        return PostScrapResponseDTO.builder().scarpId(id).build();
    }
}
