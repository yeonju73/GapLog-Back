package com.gaplog.server.domain.auth.domain.oauth;

public interface OauthInfoResponse {
    String getEmail();
    String getNickname();
    OauthProvider getOauthProvider();
    String getProfileImageUrl();
}