package com.orange.saltybread.adapters.dto;

import com.orange.saltybread.domain.aggregates.messages.Message;

import java.time.LocalDateTime;
import java.util.UUID;

public record SearchMessagesResponse(UUID senderId, String senderName, LocalDateTime timestamp, String message) {
    public static SearchMessagesResponse fromView(Message message) {
        return new SearchMessagesResponse(
                message.getSender().getId(),
                message.getSender().getName(),
                message.getTimeStamp(),
                message.getMessage()
        );
    }

}
