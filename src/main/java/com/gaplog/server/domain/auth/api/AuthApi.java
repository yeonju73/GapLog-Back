package com.gaplog.server.domain.auth.api;

import com.gaplog.server.domain.auth.application.OauthLoginService;
import com.gaplog.server.domain.auth.dto.Tokens;
import com.gaplog.server.domain.auth.dto.response.TokenResponse;
import com.gaplog.server.domain.auth.infra.google.GoogleLoginParams;
import com.gaplog.server.domain.auth.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {

    private final JwtUtil jwtUtil;
    private final OauthLoginService oauthLoginService;

    @GetMapping("/{user_id}")
    public ResponseEntity<Void> createAuthenticationToken(@PathVariable("user_id") String userId, HttpServletResponse response) {
        String accessToken = jwtUtil.generateToken(userId);

        Cookie cookie = new Cookie("access_token", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginGoogle(@RequestBody GoogleLoginParams params, HttpServletResponse response) {
        Tokens tokens = oauthLoginService.login(params);
        TokenResponse tokenResponseDto = JwtUtil.setJwtResponse(response, tokens);
        return ResponseEntity.ok(tokenResponseDto);
    }
}
