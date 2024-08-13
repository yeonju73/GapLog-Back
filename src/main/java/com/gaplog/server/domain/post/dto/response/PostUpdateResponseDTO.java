package com.gaplog.server.domain.post.dto.response;

import com.gaplog.server.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostUpdateResponseDTO {
    private Long id;
    private LocalDateTime updatedAt;

    public static PostUpdateResponseDTO of(Post post) {
        return PostUpdateResponseDTO.builder()
                .id(post.getId())
                .updatedAt(LocalDateTime.parse(post.getUpdatedAt().toString()))
                .build();
    }
}
