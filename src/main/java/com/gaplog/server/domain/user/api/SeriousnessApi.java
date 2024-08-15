package com.gaplog.server.domain.user.api;

import com.gaplog.server.domain.user.application.SeriousnessService;
import com.gaplog.server.domain.user.dto.Seriousness;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/{user_id}/seriousness")
@Tag(name = "seriousness API", description = "진지 버튼 관리 API")
public class SeriousnessApi {

    private final SeriousnessService seriousnessService;


    @GetMapping
    @Operation(summary = "유저 진지 티어 조회", description = "유저의 진지 티어 정보를 조회합니다.")
    public ResponseEntity<Seriousness> getSeriousness(@PathVariable("user_id") Long userId) {
        Seriousness seriousnessDTO = seriousnessService.getSeriousness(userId);

        return ResponseEntity.ok(seriousnessDTO);
    }
}
