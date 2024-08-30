package com.gaplog.server.domain.auth.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/google")
    public ResponseEntity.BodyBuilder loginGoogle() {
        //return ResponseEntity.ok();
        return null;
    }
}
