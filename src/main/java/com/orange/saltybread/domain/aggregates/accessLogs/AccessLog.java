package com.orange.saltybread.domain.aggregates.accessLogs;

import com.orange.saltybread.domain.aggregates.base.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "accessLogs")
@Getter
@AllArgsConstructor
public class AccessLog extends AbstractEntity {

  private UUID userId;
  private UUID roomId;
  private LocalDateTime accessTimeStamp;


}
