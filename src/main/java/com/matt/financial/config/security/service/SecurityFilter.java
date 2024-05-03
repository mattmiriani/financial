package com.matt.financial.config.security.service;

import com.matt.financial.model.service.SubjectService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final SubjectService subjectService;

    @Autowired
    public SecurityFilter(TokenService tokenService, SubjectService subjectService) {
        this.tokenService = tokenService;
        this.subjectService = subjectService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (nonNull(token)) {
            var login = this.tokenService.validateToken(token);
            var subject = this.subjectService.findByUsername(login);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(subject, null, subject.getAuthorities())
            );
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");

        if (isNull(authorization)) {
            return null;
        }

        return authorization.replace("Bearer ", "");
    }
}
