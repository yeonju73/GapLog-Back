package com.gaplog.server.domain.caterory.dao;

import com.gaplog.server.domain.caterory.domain.Category;
import com.gaplog.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 특정 사용자에 의해 생성된 카테고리 목록을 찾는 쿼리
    List<Category> findByUser(User user);
}
