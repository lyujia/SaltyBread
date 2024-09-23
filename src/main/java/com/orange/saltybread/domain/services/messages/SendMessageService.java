package com.orange.saltybread.domain.services.messages;

import com.orange.saltybread.adapters.websocket.NotificationWebSocketHandler;
import com.orange.saltybread.domain.aggregates.chatRooms.ChatRoom;
import com.orange.saltybread.domain.aggregates.messages.Message;
import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.repositories.ChatRoomRepository;
import com.orange.saltybread.domain.ports.repositories.MessageRepository;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.messages.sendMessage.SendMessageCommand;
import com.orange.saltybread.domain.ports.usecases.messages.sendMessage.SendMessageResponse;
import com.orange.saltybread.domain.ports.usecases.messages.sendMessage.SendMessageUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SendMessageService implements SendMessageUseCase {

    private MessageRepository messageRepository;
    private ChatRoomRepository chatRoomRepository;
    private UserRepository userRepository;
    private NotificationWebSocketHandler webSocketHandler;

    @Override
    @Transactional
    public SendMessageResponse sendMessage(SendMessageCommand command)
            throws UserNotFoundException, ChatRoomNotFoundException {
        Optional<User> optionalSender = userRepository.findById(command.senderId());
        if (optionalSender.isEmpty()) {
            throw new UserNotFoundException();
        }
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(command.roomId());
        if (optionalChatRoom.isEmpty()) {
            throw new ChatRoomNotFoundException();
        }
        List<UUID> userIds = optionalChatRoom.get().getMappings().stream().filter(
                        m -> m.getUser().getId().equals(command.senderId())
                ).map(m ->
                        m.getUser().getId()
                )
                .toList();
        Message message = new Message(command.message(), optionalSender.get(), command.roomId());
        messageRepository.save(message);
        for (UUID userId : userIds) {
            try {
                webSocketHandler.sendMessage(userId, message.getSender().getName() + ":" + command.message());
            } catch (IOException e) {
                continue;
            }
        }

        return new SendMessageResponse(command.senderId(), optionalSender.get().getName(),
                message.getTimeStamp(), command.message());
    }
}
