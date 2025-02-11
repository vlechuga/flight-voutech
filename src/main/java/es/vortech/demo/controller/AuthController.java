package es.vortech.demo.controller;

import es.vortech.demo.security.AuthDto;
import es.vortech.demo.dto.RegisterRequest;
import es.vortech.demo.security.TokenDto;
import es.vortech.demo.service.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(@RequestBody RegisterRequest request) {
        final TokenDto response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authenticate(@RequestBody AuthDto request) {
        final TokenDto response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public TokenDto refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication) {
        return service.refreshToken(authentication);
    }
}