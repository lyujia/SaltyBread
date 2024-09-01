package com.orange.saltybread.domain.ports.usecases.messages.sendMessage;

import java.util.UUID;

public record SendMessageCommand(UUID senderId, UUID roomId, String message) {

}
