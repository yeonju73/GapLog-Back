package com.gaplog.server.domain.caterory.dao;

import com.gaplog.server.domain.caterory.domain.ClosureCategory;
import com.gaplog.server.domain.caterory.domain.ClosureCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClosureCategoryRepository extends JpaRepository<ClosureCategory, ClosureCategoryId> {
    List<ClosureCategory> findByIdAncestorId(Long ancestorId);
}