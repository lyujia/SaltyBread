package com.orange.saltybread.domain.aggregates.verifySessions;

import com.orange.saltybread.domain.aggregates.base.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "emailSessions")
public class EmailSession extends AbstractEntity {

  private String email;
  private LocalDateTime issuedAt;
  private LocalDateTime expiredAt;

  public EmailSession(String email, LocalDateTime issuedAt,
      TemporalAmount expiryTime) {
    super();
    this.email = email;
    this.issuedAt = issuedAt;
    this.expiredAt = issuedAt.plus(expiryTime);
  }
}
