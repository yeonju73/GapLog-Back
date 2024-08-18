package com.gaplog.server.domain.user.dto.request;

import com.gaplog.server.domain.user.dto.response.PostScrapResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostScrapRequestDTO {

    private Long postId;
    private boolean scrap;


}
