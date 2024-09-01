package com.orange.saltybread.configurations;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {

  @Bean
  public byte[] secretKey(@Value("${jwt.secret}") String secretKey) throws Exception {
    return Base64.getDecoder().decode(secretKey);
  }

  @Bean
  public long expiryTime(@Value("${jwt.expiry}") long expiryTime) {
    //jwt.expiry는 분단위로 설정되어 있으니 60을 곱해 초단위로 변경해줍니다.
    return expiryTime * 60;
  }
}
