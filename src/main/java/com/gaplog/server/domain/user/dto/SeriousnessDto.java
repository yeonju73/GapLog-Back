package com.gaplog.server.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class SeriousnessDto {

    private Long seriousnessId;
    private Long userId;
    private int tier;
    private LocalDate localDate;

    public static SeriousnessDto from(com.gaplog.server.domain.user.domain.Seriousness seriousness){
        return SeriousnessDto.builder()
                .seriousnessId(seriousness.getId())
                .userId(seriousness.getUser().getId())
                .tier(seriousness.getTier())
                .localDate(seriousness.getLocalDate())
                .build();
    }
}
