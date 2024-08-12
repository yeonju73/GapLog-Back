package com.gaplog.server.domain.caterory.dao;

import com.gaplog.server.domain.caterory.domain.ClosureCategory;
import com.gaplog.server.domain.caterory.domain.ClosureCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosureCategoryRepository extends JpaRepository<ClosureCategory, ClosureCategoryId> {

}