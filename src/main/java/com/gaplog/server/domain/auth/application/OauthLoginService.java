package com.gaplog.server.domain.auth.application;

import com.gaplog.server.domain.auth.domain.OauthTokenGenerator;
import com.gaplog.server.domain.auth.domain.oauth.OauthInfoResponse;
import com.gaplog.server.domain.auth.domain.oauth.OauthLoginParams;
import com.gaplog.server.domain.auth.domain.oauth.RequestOauthInfoService;
import com.gaplog.server.domain.auth.dto.Tokens;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthLoginService {
    private final UserRepository userRepository;
    private final OauthTokenGenerator oauthTokensGenerator;
    private final RequestOauthInfoService requestOauthInfoService;

    public Tokens login(OauthLoginParams params) {
        OauthInfoResponse oauthInfoResponse = requestOauthInfoService.request(params);
        Long memberId = findOrCreateMember(oauthInfoResponse);
        return oauthTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OauthInfoResponse oauthInfoResponse) {
        return userRepository.findByEmail(oauthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newMember(oauthInfoResponse));
    }

    private Long newMember(OauthInfoResponse oauthInfoResponse) {
        User user = User.of(oauthInfoResponse.getName(),
                oauthInfoResponse.getEmail(),
                oauthInfoResponse.getOauthProvider(),
                oauthInfoResponse.getProfileImageUrl());
        return userRepository.save(user).getId();
    }
}
