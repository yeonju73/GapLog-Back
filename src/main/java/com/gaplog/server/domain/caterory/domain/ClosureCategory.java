package com.gaplog.server.domain.caterory.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClosureCategory {

    @EmbeddedId
    private ClosureCategoryId id;

    @Column(nullable = false)
    private Integer depth;

    @Builder
    public ClosureCategory(ClosureCategoryId id, Integer depth) {
        this.id = id;
        this.depth = depth;
    }

    public static ClosureCategory of(Category ancestor, Category descendant, Integer depth) {
        return ClosureCategory.builder()
                .id(new ClosureCategoryId(ancestor.getId(), descendant.getId()))
                .depth(depth)
                .build();
    }
}