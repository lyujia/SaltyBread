package com.orange.saltybread.domain.aggregates.users;

import com.orange.saltybread.domain.aggregates.base.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "loginHistories")
public class LoginHistory extends AbstractEntity {

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "userId")
  User user;
  String ip;
  String userAgent;
  LocalDateTime time;

  public LoginHistory(User user, String ip, String userAgent, LocalDateTime time) {
    super();
    this.user = user;
    this.userAgent = userAgent;
    this.ip = ip;
    this.time = time;
  }
}
