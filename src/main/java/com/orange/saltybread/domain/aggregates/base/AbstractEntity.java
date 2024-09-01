package com.orange.saltybread.domain.aggregates.base;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.util.UUID;
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractEntity {

  @Id
  private UUID id;

  public AbstractEntity() {
    this.id = UUID.randomUUID();//uuid규격확인하기
  }
}
