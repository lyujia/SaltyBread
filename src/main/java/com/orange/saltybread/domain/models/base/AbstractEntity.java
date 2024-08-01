package com.orange.saltybread.domain.models.base;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public abstract class AbstractEntity {
    @Id
    private UUID id;
    public AbstractEntity() {
        this.id = UUID.randomUUID();//uuid규격확인하기
    }
}
