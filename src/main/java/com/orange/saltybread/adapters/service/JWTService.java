package com.orange.saltybread.adapters.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.orange.saltybread.domain.aggregates.users.User;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

  private final byte[] secretKey;
  private final long accessTokenExpirationTime;

  @Autowired
  public JWTService(byte[] secretKey, long expiryTime) {
    this.secretKey = secretKey;
    this.accessTokenExpirationTime = expiryTime;
  }

  public String createAccessToken(User user) {
    return JWT.create()
        .withClaim("userId", user.getId().toString())
        .withClaim("username", user.getName())
        .withExpiresAt(Instant.now().plusSeconds(accessTokenExpirationTime))
        .sign(Algorithm.HMAC256(secretKey));
  }

  public boolean verifyAccessToken(String token) {
    try {
      JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
      return true;
    } catch (JWTVerificationException e) {
      return false;
    }
  }

  public Authentication getAuthentication(String token) {
    UUID userId = UUID.fromString(JWT.decode(token).getClaim("userId").asString());
    return new UsernamePasswordAuthenticationToken(userId, "", new ArrayList<>());
  }

  public UUID getUserId(String token) {
    return UUID.fromString(
        JWT.decode(token).getClaim("userId")
            .asString()
    );
  }

  public String getUsername(String token) {
    return JWT.decode("token").getClaim("username")
        .asString();
  }

}
