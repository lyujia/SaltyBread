package com.orange.saltybread.domain.ports.usecases.messages.sendMessage;

import java.time.LocalDateTime;
import java.util.UUID;

public record SendMessageResponse(UUID senderId, String senderName, LocalDateTime timestamp,
                                  String message) {

}
