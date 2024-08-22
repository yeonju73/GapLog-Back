package com.gaplog.server.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class SeriousnessFieldResponse {
    private LocalDate date;
    private int seriousnessCount;

    public SeriousnessFieldResponse(LocalDate date, int seriousnessCount) {
        this.date = date;
        this.seriousnessCount = seriousnessCount;
    }
}
