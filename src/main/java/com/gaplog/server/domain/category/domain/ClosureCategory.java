package com.gaplog.server.domain.category.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClosureCategory {

    @EmbeddedId
    private ClosureCategoryId id;

    @Column(nullable = false)
    private Long depth;

    @Builder
    public ClosureCategory(ClosureCategoryId id, Long depth) {
        this.id = id;
        this.depth = depth;
    }

    public static ClosureCategory of(Category ancestor, Category descendant, Long depth) {
        return ClosureCategory.builder()
                .id(new ClosureCategoryId(ancestor.getId(), descendant.getId()))
                .depth(depth)
                .build();
    }
}