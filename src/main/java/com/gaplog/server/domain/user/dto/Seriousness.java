package com.gaplog.server.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Seriousness {

    private Long seriousnessId;
    private Long userId;
    private int tier;
    private LocalDate localDate;

    public static Seriousness from(com.gaplog.server.domain.user.domain.Seriousness seriousness){
        return Seriousness.builder()
                .seriousnessId(seriousness.getId())
                .userId(seriousness.getUser().getId())
                .tier(seriousness.getTier())
                .localDate(seriousness.getLocalDate())
                .build();
    }
}
