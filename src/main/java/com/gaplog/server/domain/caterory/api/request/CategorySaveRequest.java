package com.gaplog.server.domain.caterory.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CategorySaveRequest {
    @Schema(description = "유저 id", example = "1")
    private Long userId;
    @Schema(description = "부모 카테고리 id", example = "1")
    private Long ancestorId;
    @Schema(description = "카테고리 명", example = "카테고리1")
    private String name;
}
