package com.dummy.omni_biz.authentication.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dummy.omni_biz.authentication.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String headers = request.getHeader(JwtConst.AUTHORIZATION);

        if (Strings.hasText(headers) && headers.startsWith(JwtConst.BEARER)) {
            String token = headers.substring(JwtConst.BEARER.length());
            try {
                final Claims claims = jwtUtil.validateTokenAndGetClaims(token).getPayload();
                final String username = claims.getSubject();

                @SuppressWarnings("unchecked")
                final List<String> roles = claims.get(JwtConst.ROLES, List.class);
                final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, roles.stream().map(SimpleGrantedAuthority::new).toList());

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
