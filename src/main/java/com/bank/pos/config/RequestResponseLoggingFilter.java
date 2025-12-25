package com.bank.pos.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    
    private static final List<String> SENSITIVE_FIELDS = Arrays.asList(
            "pan", "masked_pan", "zone_key", "pin_key", "mac_key", 
            "pin_block", "pin_ksn", "track2", "tlv_data"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            logRequest(wrappedRequest);
            logResponse(wrappedResponse);
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("REQUEST: ").append(request.getMethod()).append(" ").append(request.getRequestURI());
        
        if (request.getQueryString() != null) {
            logMessage.append("?").append(request.getQueryString());
        }
        
        logMessage.append(" | Headers: ").append(getRequestHeaders(request));
        
        String body = getRequestBody(request);
        if (body != null && !body.isEmpty()) {
            logMessage.append(" | Body: ").append(maskSensitiveData(body));
        }
        
        log.info(logMessage.toString());
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("RESPONSE: Status=").append(response.getStatus());
        
        String body = getResponseBody(response);
        if (body != null && !body.isEmpty()) {
            logMessage.append(" | Body: ").append(maskSensitiveData(body));
        }
        
        log.info(logMessage.toString());
    }

    private String getRequestHeaders(ContentCachingRequestWrapper request) {
        StringBuilder headers = new StringBuilder();
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            String headerValue = request.getHeader(headerName);
            // Mask Authorization header if present
            if ("authorization".equalsIgnoreCase(headerName)) {
                headerValue = "***";
            }
            headers.append(headerName).append("=").append(headerValue).append(", ");
        });
        return headers.length() > 0 ? headers.substring(0, headers.length() - 2) : "none";
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            return new String(content, StandardCharsets.UTF_8);
        }
        return null;
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            return new String(content, StandardCharsets.UTF_8);
        }
        return null;
    }

    private String maskSensitiveData(String json) {
        if (json == null || json.isEmpty()) {
            return json;
        }
        
        String masked = json;
        for (String field : SENSITIVE_FIELDS) {
            // Pattern to match JSON field: "field_name": "value"
            masked = masked.replaceAll(
                    "\"" + field + "\"\\s*:\\s*\"([^\"]+)\"",
                    "\"" + field + "\": \"***\""
            );
        }
        return masked;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Skip logging for static resources and H2 console
        return path.startsWith("/h2-console") || 
               path.startsWith("/swagger-ui") || 
               path.startsWith("/v3/api-docs") ||
               path.startsWith("/webjars");
    }
}

