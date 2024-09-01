package com.orange.saltybread.domain.aggregates.messages;

import com.orange.saltybread.domain.aggregates.base.AbstractEntity;
import com.orange.saltybread.domain.aggregates.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Table(name = "messages")
@Getter
public class Message extends AbstractEntity {

    private String message;
    private LocalDateTime timeStamp;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "senderId")
    private User sender;
    private UUID roomId;

    public Message(String message, User sender, UUID roomId) {
        super();
        this.message = message;
        this.timeStamp = LocalDateTime.now();
        this.sender = sender;
        this.roomId = roomId;
    }//메세지 생성

}
