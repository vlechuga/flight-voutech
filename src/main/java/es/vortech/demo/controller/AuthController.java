package es.vortech.demo.controller;

import es.vortech.demo.security.AuthDto;
import es.vortech.demo.dto.RegisterRequest;
import es.vortech.demo.security.TokenDto;
import es.vortech.demo.service.impl.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<Object> authenticate(@RequestBody AuthDto request, HttpServletResponse response) {
        final TokenDto tokenDto = service.authenticate(request);
        return getResponseEntity(response, tokenDto);
    }

    @PostMapping("/refresh-token")
    public TokenDto refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication) {
        return service.refreshToken(authentication);
    }

    private ResponseEntity<Object> getResponseEntity(HttpServletResponse response, TokenDto tokenDto) {
        if (tokenDto == null) {
            return ResponseEntity.badRequest().build();
        }
        getNewCookie(response, tokenDto);

        return ResponseEntity
                .ok()
                //.header(AppConstants.EXPIRATION, String.valueOf(tokenInfo.get(AppConstants.EXPIRATION)))
                //.header(AppConstants.AUTHENTICATION_TYPE, (String) tokenInfo.get(AppConstants.AUTHENTICATION_TYPE))
                .body(tokenDto);
    }

    private void getNewCookie(HttpServletResponse response, TokenDto tokenDto) {
        Cookie cookie = new Cookie("Authorization", tokenDto.getAccessToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(10 * 60);
        response.addCookie(cookie);
    }
}