package org.hankki.hankkiserver.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.auth.UserAuthentication;
import org.hankki.hankkiserver.auth.jwt.JwtProvider;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.hankki.hankkiserver.common.code.ErrorCode;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.hankki.hankkiserver.auth.UserAuthentication.createUserAuthentication;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String BEARER = "Bearer ";
    private final JwtValidator jwtValidator;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String accessToken = getAccessToken(request);
        jwtValidator.validateAccessToken(accessToken);
        doAuthentication(request, jwtProvider.getSubject(accessToken));
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER)) {
            return accessToken.substring(BEARER.length());
        }
        throw new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN);
    }

    private void doAuthentication(
            HttpServletRequest request,
            Long userId) {
        UserAuthentication authentication = createUserAuthentication(userId);
        createAndSetWebAuthenticationDetails(request, authentication);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    private void createAndSetWebAuthenticationDetails(
            HttpServletRequest request,
            UserAuthentication authentication) {
        WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
        WebAuthenticationDetails webAuthenticationDetails = webAuthenticationDetailsSource.buildDetails(request);
        authentication.setDetails(webAuthenticationDetails);
    }
}
