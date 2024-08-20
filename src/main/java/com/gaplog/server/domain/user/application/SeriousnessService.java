package com.gaplog.server.domain.user.application;
import com.gaplog.server.domain.user.dao.SeriousnessRepository;
import com.gaplog.server.domain.user.dto.SeriousnessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeriousnessService {
    private final SeriousnessRepository seriousnessRepository;

    @Transactional(readOnly = true)
    public SeriousnessDto getSeriousness(Long userId) {
        com.gaplog.server.domain.user.domain.Seriousness seriousness = seriousnessRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        return SeriousnessDto.from(seriousness);
    }

}
