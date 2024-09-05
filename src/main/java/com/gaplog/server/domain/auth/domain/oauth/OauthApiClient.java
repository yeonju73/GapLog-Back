package com.gaplog.server.domain.auth.domain.oauth;

public interface OauthApiClient {
    OauthProvider oauthProvider();
    String requestAccessToken(OauthLoginParams params);
    OauthInfoResponse requestOauthInfo(String accessToken);
}

