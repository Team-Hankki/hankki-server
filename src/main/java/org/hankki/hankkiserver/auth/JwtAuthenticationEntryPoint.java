package org.hankki.hankkiserver.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        handleException(response);
    }

    private void handleException(HttpServletResponse response) throws IOException {
        setResponse(response, HttpStatus.UNAUTHORIZED, AuthErrorCode.UNAUTHORIZED);
    }

    private void setResponse(
            HttpServletResponse response,
            HttpStatus httpStatus,
            AuthErrorCode authErrorCode) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(httpStatus.value());
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(HankkiResponse.of(authErrorCode)));
    }
}
