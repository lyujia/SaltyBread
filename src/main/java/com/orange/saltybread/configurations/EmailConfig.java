package com.orange.saltybread.configurations;

import java.security.Key;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

  @Bean
  public TemporalAmount emailExpiration(@Value("${email.expiry-in-minutes}") long expiryInMinutes) {
    return Duration.ofMinutes(expiryInMinutes);
  }

  @Bean
  public String emailHost(@Value("${email.host}") String host) {
    return host;
  }

  @Bean
  public int emailPort(@Value("${email.port}") int port) {
    return port;
  }

  @Bean
  public String emailUsername(@Value("${email.username}") String username) {
    return username;
  }

  @Bean
  public String emailPassword(@Value("${email.password}") String password) {
    return password;
  }

  @Bean
  public String emailChallengeUrl(@Value("${email.challenge-url}") String challengeUrl) {
    return challengeUrl;
  }

  @Bean
  public Key emailSecret(@Value("${email.secret}") String secret) {
    byte[] secretBytes = Base64.getDecoder().decode(secret);
    return new SecretKeySpec(secretBytes, "HmacSHA256");
  }
}
