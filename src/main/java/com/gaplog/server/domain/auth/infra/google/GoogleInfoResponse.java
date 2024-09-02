package com.gaplog.server.domain.auth.infra.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaplog.server.domain.auth.domain.oauth.OauthInfoResponse;
import com.gaplog.server.domain.auth.domain.oauth.OauthProvider;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleInfoResponse implements OauthInfoResponse {

    @JsonProperty("google_account")
    private GoogleAccount googleAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class GoogleAccount {
        private GoogleProgile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class GoogleProgile {
        @JsonProperty("profile_image_url")
        private String profileImageURL;
        private String nickname;
    }

    @Override
    public String getNickname() {
        return googleAccount.profile.nickname;
    }

    @Override
    public OauthProvider getOauthProvider() {
        return OauthProvider.GOOGLE;
    }

    @Override
    public String getEmail() {
        return googleAccount.email;
    }

    @Override
    public String getProfileImageUrl() {
        return googleAccount.profile.profileImageURL;
    }
}
