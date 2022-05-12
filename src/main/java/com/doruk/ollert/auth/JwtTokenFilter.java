package com.doruk.ollert.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    final TokenManager tokenManager;

    public JwtTokenFilter(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String auth = req.getHeader("Authorization");
        String token = null;
        String username = null;

        if (req.getRequestURI().contains("auth") || req.getRequestURI().contains("h2-console")) {
            filterChain.doFilter(req, res);
            return;
        }

        if (auth != null && auth.contains("Bearer")) {
            token = auth.split(" ")[1];
            try {
                username = tokenManager.getUsername(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (username != null && token != null
                && SecurityContextHolder.getContext().getAuthentication() == null
                && tokenManager.validateToken(token)) {
            UsernamePasswordAuthenticationToken userPassToken = new UsernamePasswordAuthenticationToken(
                    username, null, new ArrayList<>());
            userPassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(userPassToken);

        }
        filterChain.doFilter(req, res);

    }
}
