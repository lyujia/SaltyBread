package com.orange.saltybread.domain.ports.usecases.messages.getMessages;

import java.util.UUID;

public record GetMessageCommand(UUID roomId) {

}
