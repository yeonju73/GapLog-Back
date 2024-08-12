package com.gaplog.server.domain.user.dto;

import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.domain.Seriousness;
import com.gaplog.server.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class SeriousnessDTO {

    private Long seriousnessId;
    private Long userId;
    private int tier;
    private LocalDate localDate;

    public static SeriousnessDTO from(Seriousness seriousness){
        return SeriousnessDTO.builder()
                .seriousnessId(seriousness.getId())
                .userId(seriousness.getUser().getId())
                .tier(seriousness.getTier())
                .localDate(seriousness.getLocalDate())
                .build();
    }
}
