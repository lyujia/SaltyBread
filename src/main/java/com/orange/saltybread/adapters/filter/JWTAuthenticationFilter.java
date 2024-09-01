package com.orange.saltybread.adapters.filter;

import com.orange.saltybread.adapters.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private final JWTService jwtService;
  private final static String TOKEN_PREFIX = "Bearer ";
  private final static String AUTHORIZATION_HEADER = "Authorization";

  public JWTAuthenticationFilter(JWTService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain)
      throws IOException, ServletException {
    String authorizationHeaderValue = request.getHeader(AUTHORIZATION_HEADER);
    if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith(TOKEN_PREFIX)) {
      String token = authorizationHeaderValue.substring(TOKEN_PREFIX.length());
      if (jwtService.verifyAccessToken(token)) {
        Authentication authentication = jwtService.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    chain.doFilter(request, response);
  }
}
