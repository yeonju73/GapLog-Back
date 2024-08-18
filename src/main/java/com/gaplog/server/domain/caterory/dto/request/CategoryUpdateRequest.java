package com.gaplog.server.domain.caterory.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CategoryUpdateRequest {
    @Schema(description = "카테고리 id", example = "1")
    private Long categoryId;
    @Schema(description = "부모 카테고리 id", example = "1")
    private Long ancestorId;
}

