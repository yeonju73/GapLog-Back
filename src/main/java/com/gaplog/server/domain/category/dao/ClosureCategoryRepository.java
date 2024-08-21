package com.gaplog.server.domain.category.dao;

import com.gaplog.server.domain.category.domain.ClosureCategory;
import com.gaplog.server.domain.category.domain.ClosureCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClosureCategoryRepository extends JpaRepository<ClosureCategory, ClosureCategoryId> {
    List<ClosureCategory> findByIdAncestorId(Long ancestorId);
    List<ClosureCategory> findByIdDescendantId(Long descendantId);
    List<ClosureCategory> findByIdAncestorIdInAndDepth(List<Long> categoryIds, Long depth);
    Long countByIdDescendantId(Long categoryId);
}