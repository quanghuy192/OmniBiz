package com.dummy.omni_biz.authentication.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dummy.omni_biz.authentication.entity.AuthRequest;
import com.dummy.omni_biz.authentication.service.JwtService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {
        final Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        if (authentication.isAuthenticated()) {
            final String token = jwtService.generateToken(authentication.getName());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.notFound().build();
    }
}
