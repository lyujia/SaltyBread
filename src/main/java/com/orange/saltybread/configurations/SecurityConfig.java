package com.orange.saltybread.configurations;


import com.orange.saltybread.adapters.filter.JWTAuthenticationFilter;
import com.orange.saltybread.adapters.service.JWTService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JWTService jwtService;

  @Autowired
  public SecurityConfig(JWTService jwtService) {
    this.jwtService = jwtService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorize -> authorize
                .requestMatchers("/users/login").anonymous()
                .requestMatchers("/email/verifications").anonymous()
                .requestMatchers(HttpMethod.POST, "/users").anonymous()
                .requestMatchers("/notifications").anonymous()
                .anyRequest().authenticated()
        )
        .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
        .addFilterBefore(new JWTAuthenticationFilter(jwtService),
            UsernamePasswordAuthenticationFilter.class);

    ;

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(List.of("*")); // 모든 도메인 허용
    configuration.setAllowedMethods(List.of("*")); // 모든 HTTP 메서드 허용
    configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
    configuration.setAllowCredentials(true); // 자격 증명 허용 (선택 사항)

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용
    return source;
  }

}
