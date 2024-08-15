package com.gaplog.server.domain.user.application;

import com.gaplog.server.domain.post.dao.PostRepository;
import com.gaplog.server.domain.user.dao.SeriousnessRepository;
import com.gaplog.server.domain.user.domain.Seriousness;
import com.gaplog.server.domain.user.dto.SeriousnessDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
public class SeriousnessService {
    private final SeriousnessRepository seriousnessRepository;

    @Transactional(readOnly = true)
    public SeriousnessDTO getSeriousness(Long userId) {
        Seriousness seriousness = (Seriousness) seriousnessRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        return SeriousnessDTO.from(seriousness);
    }

}
