package com.gaplog.server.domain.user.dao;

import com.gaplog.server.domain.user.domain.Seriousness;
import com.gaplog.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeriousnessRepository extends JpaRepository <Seriousness, Long> {
    Optional<Seriousness> findByUserId(Long userId);
}
