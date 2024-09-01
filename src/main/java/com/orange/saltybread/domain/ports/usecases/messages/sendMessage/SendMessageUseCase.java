package com.orange.saltybread.domain.ports.usecases.messages.sendMessage;

import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.errors.UserNotFoundException;

public interface SendMessageUseCase {

    public SendMessageResponse sendMessage(SendMessageCommand command) throws UserNotFoundException, ChatRoomNotFoundException;
}
