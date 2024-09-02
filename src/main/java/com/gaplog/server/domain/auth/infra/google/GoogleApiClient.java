package com.gaplog.server.domain.auth.infra.google;

import com.gaplog.server.domain.auth.domain.oauth.OauthApiClient;
import com.gaplog.server.domain.auth.domain.oauth.OauthInfoResponse;
import com.gaplog.server.domain.auth.domain.oauth.OauthLoginParams;
import com.gaplog.server.domain.auth.domain.oauth.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GoogleApiClient implements OauthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.google.url.auth}")
    private String authUrl;

    @Value("${oauth.google.url.api}")
    private String apiUrl;

    @Value("${oauth.google.client-id}")
    private String clientId;

    @Value("${oauth.google.client-secret}")
    private String clientSecret;

    @Value("${oauth.google.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate;

    @Override
    public OauthProvider oauthProvider() {
        return OauthProvider.GOOGLE;
    }

    @Override
    public String requestAccessToken(OauthLoginParams params) {
        String url = authUrl + "/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        //body.add("code", params.getAuthorizationCode());
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", GRANT_TYPE);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        GoogleTokens response = restTemplate.postForObject(url, request, GoogleTokens.class);

        assert response != null;
        return response.getAccessToken();
    }

    @Override
    public OauthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/userinfo";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        return restTemplate.postForObject(url, request, GoogleInfoResponse.class);
    }
}
