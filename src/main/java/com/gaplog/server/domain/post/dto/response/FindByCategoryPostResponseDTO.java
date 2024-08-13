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
public class FindByCategoryPostResponseDTO {

    //comment
    private Long id;
    private String title;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likeCount;
    private int jinjiCount;

    public static FindByCategoryPostResponseDTO of(Post post) {
        return FindByCategoryPostResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .thumbnailUrl(post.getThumbnailUrl())
                .likeCount(post.getLikeCount())
                .jinjiCount(post.getJinjiCount())
                .createdAt(LocalDateTime.parse(post.getCreatedAt().toString()))
                .updatedAt(LocalDateTime.parse(post.getUpdatedAt().toString()))
                .build();
    }
}
