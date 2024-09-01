package com.orange.saltybread.domain.services.rooms;

import com.orange.saltybread.domain.aggregates.chatRooms.ChatRoom;
import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.ports.repositories.ChatRoomRepository;
import com.orange.saltybread.domain.ports.usecases.chatRooms.updateRoomName.UpdateRoomTitleCommand;
import com.orange.saltybread.domain.ports.usecases.chatRooms.updateRoomName.UpdateRoomTitleResponse;
import com.orange.saltybread.domain.ports.usecases.chatRooms.updateRoomName.UpdateRoomTitleUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateRoomTitleService implements UpdateRoomTitleUseCase {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    @Transactional
    public UpdateRoomTitleResponse updateRoomTitle(UpdateRoomTitleCommand command) throws ChatRoomNotFoundException {
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(command.roomId());
        if (optionalChatRoom.isEmpty()) {
            throw new ChatRoomNotFoundException();
        }
        ChatRoom chatRoom = optionalChatRoom.get();
        chatRoom.setTitle(command.title());
        chatRoomRepository.save(chatRoom);
        return new UpdateRoomTitleResponse();
    }
}
